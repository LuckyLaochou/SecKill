package cn.laochou.seckill.config;

import cn.laochou.seckill.pojo.User;
import cn.laochou.seckill.service.UserService;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
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
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        if(request != null && response != null) {
            String paramToken = request.getParameter(UserService.COOKIE_NAME);
            String cookieToken = getCookieToken(request, UserService.COOKIE_NAME);
            if(ObjectUtils.isEmpty(paramToken) && ObjectUtils.isEmpty(cookieToken)) {
                return null;
            }
            String token = ObjectUtils.isEmpty(paramToken) ? cookieToken : paramToken;
            return userService.getUserByToken(response, token);
        }
        return null;
    }

    private String getCookieToken(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
