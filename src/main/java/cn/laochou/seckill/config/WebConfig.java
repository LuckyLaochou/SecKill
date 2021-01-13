package cn.laochou.seckill.config;

import cn.laochou.seckill.access.AccessInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource(name = "userArgumentResolver")
    private UserArgumentResolver userArgumentResolver;


    @Resource(name = "accessInterceptor")
    private AccessInterceptor accessInterceptor;

    /**
     * 添加参数解析器
     * 我们Controller里面的方法带的参数，就是通过参数解析器传的
     * @param resolvers 一系列解析器
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userArgumentResolver);
    }


    /**
     * 添加拦截器
     * @param registry 拦截器注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessInterceptor);
    }
}
