package cn.laochou.seckill.access;

import cn.laochou.seckill.exception.GlobalException;
import cn.laochou.seckill.pojo.User;
import cn.laochou.seckill.redis.key.impl.AccessKeyPrefix;
import cn.laochou.seckill.result.CodeMessage;
import cn.laochou.seckill.service.RedisService;
import cn.laochou.seckill.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 定义访问拦截器
 */
@Service
public class AccessInterceptor implements HandlerInterceptor {

    @Resource(name = "userService")
    private UserService userService;


    @Resource(name = "redisService")
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);
            // 没有加我们的 AccessLimit 限制，直接放行
            if(accessLimit == null) return true;
            int seconds = accessLimit.seconds();
            int count = accessLimit.count();
            boolean needLogin = accessLimit.needLogin();
            // 注意，在获取的时候，如果没有就已经抛出异常了，所以这里是一定获取到了的
            String key = request.getRequestURI();
            if(needLogin) {
                User user = userService.getUserFromRequestTokenOrCookieToken(request, response);
                UserContext.setUser(user);
                key = String.format("%s_%s", key, user.getId());
            }

            // 从Redis中获取
            Integer countInRedis = redisService.get(AccessKeyPrefix.PREFIX_ACCESS, key, Integer.class);
            AccessKeyPrefix.PREFIX_ACCESS.updateExpireSeconds(seconds);
            if(countInRedis == null) {
                // 证明是第一次访问
                redisService.set(AccessKeyPrefix.PREFIX_ACCESS, key, 1);
            }else if(countInRedis < count) {
                redisService.incr(AccessKeyPrefix.PREFIX_ACCESS, key);
            }else {
                throw new GlobalException(CodeMessage.ACCESS_FREQUENT);
            }
            return true;
        }
        return false;
    }

}
