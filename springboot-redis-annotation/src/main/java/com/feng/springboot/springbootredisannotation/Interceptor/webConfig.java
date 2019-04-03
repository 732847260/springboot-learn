package com.feng.springboot.springbootredisannotation.Interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @Author: LiangZF
 * @Description:
 * @Date: Created in 15:32 2019/3/22
 * @Modified By: LiangZF
 */
@Configuration
public class webConfig implements WebMvcConfigurer {

    @Resource
    private sessionInterceptor sessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 自定义拦截器，添加拦截路径和排除拦截路径
        registry.addInterceptor(sessionInterceptor).addPathPatterns("/**").excludePathPatterns("/userlogin");
    }

}
