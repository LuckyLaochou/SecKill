package cn.laochou.seckill.controller;

import cn.laochou.seckill.pojo.User;
import cn.laochou.seckill.result.CodeMessage;
import cn.laochou.seckill.result.Result;
import cn.laochou.seckill.service.GoodsService;
import cn.laochou.seckill.service.RedisService;
import cn.laochou.seckill.service.UserService;
import cn.laochou.seckill.util.DateUtil;
import cn.laochou.seckill.vo.GoodsVO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    private static final Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Resource(name = "goodsService")
    private GoodsService goodsService;

    @RequestMapping("/list")
    @ResponseBody
    public Result<String> getGoodsList(User user) {
        JSONObject result = new JSONObject();
        result.put("user", user);
        List<GoodsVO> goodsVOList = goodsService.getGoodsVOList();
        result.put("goodsVOList", goodsVOList);
        return Result.success(result.toJSONString());
    }

    @RequestMapping("/detail/{id}")
    @ResponseBody
    public Result<String> getGoodsVODetailByID(@PathVariable("id") int id, User user) {
        JSONObject result = new JSONObject();
        result.put("user", user);
        GoodsVO goodsVO = goodsService.getGoodsVOById(id);


        int seckillStatus = 0;
        // 倒计时时间
        int remainSeconds = 0;

        // 逻辑判断
        // 开始时间
        long startDate = DateUtil.getDateTime(goodsVO.getStartDate());
        // 截止时间
        long endDate = DateUtil.getDateTime(goodsVO.getEndDate());
        // 当前时间
        long now = System.currentTimeMillis();
        if(now < startDate) {
            // 秒杀还没开始
            remainSeconds = (int) ((startDate - now) / 1000);
        }else if(now > endDate) {
            // 秒杀已经结束了
            seckillStatus = 2;
            remainSeconds = -1;
        }else {
            // 秒杀进行中
            seckillStatus = 1;
            result.put("goods", goodsVO);
        }
        result.put("seckillStatus", seckillStatus);
        result.put("remainSeconds", remainSeconds);
        return Result.success(result.toJSONString());
    }


}
