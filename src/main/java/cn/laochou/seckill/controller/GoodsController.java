package cn.laochou.seckill.controller;

import cn.laochou.seckill.enums.SeckillStatusEnum;
import cn.laochou.seckill.pojo.User;
import cn.laochou.seckill.result.CodeMessage;
import cn.laochou.seckill.result.Result;
import cn.laochou.seckill.service.GoodsService;
import cn.laochou.seckill.vo.GoodsDetailVO;
import cn.laochou.seckill.vo.GoodsVO;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/goods")
public class GoodsController {


//    @Resource(name = "goodsService")
//    private GoodsService goodsService;
    private final GoodsService goodsService;

    public GoodsController(@Autowired GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @RequestMapping("/list")
    @ResponseBody
    public Result<String> getGoodsList(User user) {
        JSONObject result = new JSONObject();
        result.put("user", user);
        // 所有业务代码都应该在Service里面
        String cacheGoodsVO = goodsService.getGoodsList();
        result.put("data", cacheGoodsVO);
        return Result.success(result.toJSONString());
    }

    @RequestMapping("/detail/{id}")
    @ResponseBody
    public Result<GoodsDetailVO> getGoodsVODetailByID(@PathVariable("id") Long id, User user) {
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
