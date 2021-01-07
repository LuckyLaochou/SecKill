package cn.laochou.seckill.controller;

import cn.laochou.seckill.result.CodeMessage;
import cn.laochou.seckill.result.Result;
import cn.laochou.seckill.utils.ValidatorUtil;
import cn.laochou.seckill.vo.LoginVO;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoggerFactory.class);


    @RequestMapping("/login")
    @ResponseBody
    public Result<Boolean> login(@RequestBody LoginVO vo) {
        logger.info(JSON.toJSONString(vo));
        String mobile = vo.getMobile();
        String password = vo.getPassword();
        // 参数校验
        if(ObjectUtils.isEmpty(password) || ObjectUtils.isEmpty(mobile)) {
            return Result.error(CodeMessage.PARAM_ERROR);
        }
        // 手机格式判断
        else if(!ValidatorUtil.isMobile(mobile)) {
            return Result.error(CodeMessage.MOBILE_FORMAT_ERROR);
        }

        return Result.success(Boolean.TRUE);
    }

}
