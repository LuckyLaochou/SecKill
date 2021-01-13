package cn.laochou.seckill.controller;

import cn.laochou.seckill.access.AccessLimit;
import cn.laochou.seckill.enums.SeckillStatusEnum;
import cn.laochou.seckill.pojo.SeckillOrder;
import cn.laochou.seckill.pojo.User;
import cn.laochou.seckill.rabbitmq.MQSender;
import cn.laochou.seckill.redis.key.impl.GoodsKeyPrefix;
import cn.laochou.seckill.result.CodeMessage;
import cn.laochou.seckill.result.Result;
import cn.laochou.seckill.pojo.SeckillMessage;
import cn.laochou.seckill.service.GoodsService;
import cn.laochou.seckill.service.OrderService;
import cn.laochou.seckill.service.RedisService;
import cn.laochou.seckill.service.SeckillService;
import cn.laochou.seckill.vo.GoodsVO;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 秒杀核心
 */
@Controller
public class SeckillController implements InitializingBean {


    @Resource(name = "goodsService")
    private GoodsService goodsService;

    @Resource(name = "orderService")
    private OrderService orderService;

    @Resource(name = "seckillService")
    private SeckillService seckillService;

    @Resource(name = "redisService")
    private RedisService redisService;

    @Autowired
    private MQSender mqSender;

    // 内存标记
    private final Map<Long, Boolean> map = new HashMap<Long, Boolean>();


    /**
     * 在SeckillController 加载完成之后，执行这个方法
     * 在Spring IOC 的执行流程便可以知道这个方法
     * 将我们的库存加入到Redis中
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVO> goodsVOList = goodsService.getGoodsVOList();
        System.out.println(JSONObject.toJSONString(goodsVOList));
        if(goodsVOList == null) return;
        // 如果我们的GoodsVOList不为空，那么就将list中的商品添加到我们的Redis中
        for(GoodsVO goodsVO : goodsVOList) {
            // 将商品的库存添加到我们的Redis中
            redisService.set(GoodsKeyPrefix.PREFIX_SECKILL_GOODSSTOCK,
                    String.valueOf(goodsVO.getId()), goodsVO.getStockCount());
            map.put(goodsVO.getId(), false);
        }
    }


    @RequestMapping("/{path}/seckill/{goodsId}")
    @ResponseBody
    public Result<String> seckill(User user, @PathVariable("goodsId")Long goodsId, @PathVariable("path") String path) {
        // 我们需要先对我们的链接进行判断
        if(path == null || path.isEmpty()) return Result.error(CodeMessage.PARAM_ERROR);
        boolean check = seckillService.checkPath(user, goodsId, path);
        if(!check) return Result.error(CodeMessage.PATH_ILLEGAL);
        boolean over = map.get(goodsId);
        if(over) return Result.error(CodeMessage.SECKILL_FAIL);
        // 预减库存
        long stock = redisService.decr(GoodsKeyPrefix.PREFIX_SECKILL_GOODSSTOCK,
                String.valueOf(goodsId));
        if(stock < 0) {
            map.put(goodsId, true);
            return Result.error(CodeMessage.SECKILL_FAIL);
        }

        // 判断是否已经秒杀到了
        SeckillOrder order = orderService.getSeckillOrderByUserIdAndGoodsId(user.getId(), goodsId);
        if(order != null) return Result.error(CodeMessage.SECKILL_ALREADY);

        // 通过MQ入队
        SeckillMessage seckillMessage = new SeckillMessage();
        seckillMessage.setGoodsId(goodsId);
        seckillMessage.setUser(user);
        mqSender.sendSeckillMessage(seckillMessage);

        // 返回排队中的状态
        return Result.error(CodeMessage.BE_QUEUEING);
//        // 判断库存
//        GoodsVO goodsVO = goodsService.getGoodsVOById(goodsId);
//        if(goodsVO == null) return Result.error(CodeMessage.GOODS_NOT_EXIST);
//        if(goodsVO.getStockCount() <= 0) return Result.error(CodeMessage.GOODS_STOCK_EMPTY);
//        // 判断是否已经秒杀到了
//        SeckillOrder order = orderService.getSeckillOrderByUserIdAndGoodsId(user.getId(), goodsId);
//        if(order != null) return Result.error(CodeMessage.SECKILL_ALREADY);
//        // 减库存，下订单，写入秒杀订单（这个流程具备事务）
//        OrderInfo orderInfo = seckillService.seckill(user, goodsVO);
//        result.put("order", orderInfo);
//        result.put("goods", goodsVO);
//        return Result.success(result.toJSONString());
    }


    /**
     * 查询秒杀的结果
     * @return 返回秒杀的订单号
     */
    @RequestMapping("/seckill/result")
    @ResponseBody
    public Result<Long> seckillStatus(User user, @RequestParam(name = "goodsId") Long goodsId) {
        Long orderId = seckillService.getSeckillResult(user.getId(), goodsId);
        if(orderId == SeckillStatusEnum.SECKILL_FAIL.getStatus()) return Result.error(CodeMessage.SECKILL_FAIL);
        if(orderId == SeckillStatusEnum.SECKILL_BE_QUEUEING.getStatus()) return Result.error(CodeMessage.BE_QUEUEING);
        return Result.success(orderId);
    }


    /**
     * 获取秒杀链接
     * @param user 用户
     * @param goodsId 商品ID
     * @return 秒杀链接
     */
    @AccessLimit(seconds = 5, count = 5)
    @RequestMapping("/seckill/path")
    @ResponseBody
    public Result<String> getSeckillPath(User user, @RequestParam(name = "goodsId") Long goodsId) {
        if(goodsId == null) return Result.error(CodeMessage.PASSWORD_ERROR);
        // 在这里，我们主要是生成我们的path
        String path = seckillService.createSeckillPath(user, goodsId);
        return Result.success(path);
    }


}
