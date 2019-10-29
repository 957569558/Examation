package com.zzkk;

import com.zzkk.interceptor.WebAppInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author warmli
 * 拦截器
 */

@Configuration
public class WebAppConfiguration implements WebMvcConfigurer {
    /** 拦截器拦截路径 */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new WebAppInterceptor())
                .addPathPatterns("/opop");
    }
}
