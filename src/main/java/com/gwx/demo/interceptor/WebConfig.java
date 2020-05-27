package com.gwx.demo.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 配置拦截器时，原来继承的WebMvcConfiguerAdapter已过时，报错：java WebMvcConfigurerAdapter is deprecated
     * 解决方案：实现WebMvcConfigurer
     *  解决跨域问题
     * public void addCorsMappings(CorsRegistry registry) ;
     *  添加拦截器
     * void addInterceptors(InterceptorRegistry registry);
     *  这里配置视图解析器
     * void configureViewResolvers(ViewResolverRegistry registry);
     *  配置内容裁决的一些选项
     * void configureContentNegotiation(ContentNegotiationConfigurer configurer);
     *  视图跳转控制器
     * void addViewControllers(ViewControllerRegistry registry);
     *  静态资源处理
     * void addResourceHandlers(ResourceHandlerRegistry registry);
     *  默认静态资源处理器
     *
     * **/
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login")
                .excludePathPatterns("/register")
                .excludePathPatterns("/static/**");
        /*
        * 注意：这里应排除掉静态资源的路径 /static/** 不然无法引用到静态资源
        * 结合thymeleaf会出现就算配置了也引用不到的现象；
        解决：
        1 在application。properties中配置静态资源的地址：spring.mvc.static-path-pattern=/static/**
        2 上面的防止拦截
        3 在引用资源的标签中th:src或者th:href="@{/static/...}"加上/static/
         */
    }

}
