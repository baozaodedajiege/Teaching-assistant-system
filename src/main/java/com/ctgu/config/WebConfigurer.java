package com.ctgu.config;

import com.ctgu.common.Const;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfigurer extends WebMvcConfigurerAdapter {


    /**
     * 配置静态访问资源
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //自定义项目内目录
        //registry.addResourceHandler("/my/**").addResourceLocations("classpath:/my/");
        //指向外部目录
        registry.addResourceHandler("/upload/**").addResourceLocations(Const.UPLOAD_FILE_PATH);
        super.addResourceHandlers(registry);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(new InterceptorConfig());
        //
        //addInterceptor.excludePathPatterns("/contest/index");
        //addInterceptor.excludePathPatterns("/problemset/list");
        addInterceptor.excludePathPatterns("/manage/login");
        addInterceptor.excludePathPatterns("/contest/index");
        addInterceptor.excludePathPatterns("/account/api/**");
        //addInterceptor.excludePathPatterns("/questionnaire/**");
        //
        //
        //addInterceptor.addPathPatterns("/contest/**");
        addInterceptor.addPathPatterns("/account/**");
        addInterceptor.addPathPatterns("/contest/**");
        addInterceptor.addPathPatterns("/manage/**");
    }
}