package cn.laochou.seckill.config;

import cn.laochou.seckill.access.UserContext;
import cn.laochou.seckill.exception.GlobalException;
import cn.laochou.seckill.pojo.User;
import cn.laochou.seckill.result.CodeMessage;
import cn.laochou.seckill.service.UserService;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Resource(name = "userService")
    private UserService userService;

    /**
     * 是否支持参数
     * @param methodParameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> clazz = methodParameter.getParameterType();
        return clazz == User.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        if(request != null && response != null) {
            // 先从UserContext来获取是否存在User
            // 因为对于AccessLimit可能存在没有打在需要登录的业务上
            User user = UserContext.getUser();
            if(user != null) return user;
            return userService.getUserFromRequestTokenOrCookieToken(request, response);
        }
        throw new GlobalException(CodeMessage.SERVER_ERROR);
    }


}
