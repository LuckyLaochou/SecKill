package cn.laochou.seckill.controller;

import cn.laochou.seckill.result.CodeMessage;
import cn.laochou.seckill.result.Result;
import cn.laochou.seckill.service.UserService;
import cn.laochou.seckill.vo.LoginVO;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoggerFactory.class);


    @Resource(name = "userService")
    private UserService userService;


    @RequestMapping("/login")
    @ResponseBody
    public Result<Boolean> login(@Valid @RequestBody LoginVO loginVO) {
        logger.info(JSON.toJSONString(loginVO));
        // 登陆
        userService.login(loginVO);
        return Result.success(true);
    }

}
