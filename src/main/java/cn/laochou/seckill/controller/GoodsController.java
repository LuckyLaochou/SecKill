package cn.laochou.seckill.controller;

import cn.laochou.seckill.pojo.User;
import cn.laochou.seckill.result.CodeMessage;
import cn.laochou.seckill.result.Result;
import cn.laochou.seckill.service.RedisService;
import cn.laochou.seckill.service.UserService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    private static final Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "redisService")
    private RedisService redisService;

    @RequestMapping("/list")
    @ResponseBody
    public Result<String> getGoodsList(HttpServletResponse response, User user) {
        if(user == null) {
            return Result.error(CodeMessage.NOT_LOGIN);
        }
        logger.info(JSON.toJSONString(user));
        return Result.success(JSON.toJSONString(user));
    }

}
