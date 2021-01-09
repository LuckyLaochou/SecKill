package cn.laochou.seckill.controller;

import cn.laochou.seckill.pojo.OrderInfo;
import cn.laochou.seckill.pojo.SeckillOrder;
import cn.laochou.seckill.pojo.User;
import cn.laochou.seckill.result.CodeMessage;
import cn.laochou.seckill.result.Result;
import cn.laochou.seckill.service.GoodsService;
import cn.laochou.seckill.service.OrderService;
import cn.laochou.seckill.service.SeckillService;
import cn.laochou.seckill.vo.GoodsVO;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 秒杀核心
 */
@Controller
public class SeckillController {


    @Resource(name = "goodsService")
    private GoodsService goodsService;

    @Resource(name = "orderService")
    private OrderService orderService;

    @Resource(name = "seckillService")
    private SeckillService seckillService;


    @RequestMapping("/seckill/{goodsId}")
    @ResponseBody
    public Result<String> seckill(User user, @PathVariable("goodsId")int goodsId) {
        JSONObject result = new JSONObject();
        result.put("user", user);
        // 判断库存
        GoodsVO goodsVO = goodsService.getGoodsVOById(goodsId);
        if(goodsVO == null) return Result.error(CodeMessage.GOODS_NOT_EXIST);
        if(goodsVO.getStockCount() <= 0) return Result.error(CodeMessage.GOODS_STOCK_EMPTY);
        // 判断是否已经秒杀到了
        SeckillOrder order = orderService.getSeckillOrderByUserIdAndGoodsId(user.getId(), goodsId);
        if(order != null) return Result.error(CodeMessage.SECKILL_ALREADY);
        // 减库存，下订单，写入秒杀订单（这个流程具备事务）
        OrderInfo orderInfo = seckillService.seckill(user, goodsVO);
        result.put("order", orderInfo);
        result.put("goods", goodsVO);
        return Result.success(result.toJSONString());
    }


}
