package cn.laochou.seckill.controller;

import cn.laochou.seckill.enums.SeckillStatusEnum;
import cn.laochou.seckill.pojo.User;
import cn.laochou.seckill.redis.key.impl.GoodsKeyPrefix;
import cn.laochou.seckill.result.CodeMessage;
import cn.laochou.seckill.result.Result;
import cn.laochou.seckill.service.GoodsService;
import cn.laochou.seckill.service.RedisService;
import cn.laochou.seckill.vo.GoodsDetailVO;
import cn.laochou.seckill.vo.GoodsVO;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    private static final Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Resource(name = "goodsService")
    private GoodsService goodsService;

    @Resource(name = "redisService")
    private RedisService redisService;

    @RequestMapping("/list")
    @ResponseBody
    public Result<String> getGoodsList(User user) {
        JSONObject result = new JSONObject();
        result.put("user", user);
        // 从缓冲里面拿列表转换成JSON的字符。
        String cacheGoodsVO = redisService.get(GoodsKeyPrefix.PREFIX_GOODSLIST, "", String.class);
        if(cacheGoodsVO == null) {
            List<GoodsVO> goodsVOList = goodsService.getGoodsVOList();
            cacheGoodsVO = JSONObject.toJSONString(goodsVOList);
            redisService.set(GoodsKeyPrefix.PREFIX_GOODSLIST, "", cacheGoodsVO);
        }
        result.put("data", cacheGoodsVO);
        return Result.success(result.toJSONString());
    }

    @RequestMapping("/detail/{id}")
    @ResponseBody
    public Result<GoodsDetailVO> getGoodsVODetailByID(@PathVariable("id") int id, User user) {
        GoodsDetailVO goodsDetailVO = new GoodsDetailVO();
        goodsDetailVO.setUser(user);
        GoodsVO goodsVO = goodsService.getGoodsVOById(id);
        if(goodsVO == null) return Result.error(CodeMessage.GOODS_NOT_EXIST);
        // 秒杀状态
        int seckillStatus = goodsService.getSeckillStatus(goodsVO);
        // 倒计时时间
        int remainSeconds = seckillStatus == SeckillStatusEnum.SECKILL_NOT_BEGIN.getStatus() ? goodsService.getRemainSeconds(goodsVO) : -1;
        if(seckillStatus == SeckillStatusEnum.SECKILL_ING.getStatus()) goodsDetailVO.setGoodsVO(goodsVO);
        goodsDetailVO.setSeckillStatus(seckillStatus);
        goodsDetailVO.setRemainSeconds(remainSeconds);
        return Result.success(goodsDetailVO);
    }


}
