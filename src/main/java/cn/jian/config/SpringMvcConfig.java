package cn.jian.config;

import cn.jian.inteceptor.LoginInteceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan(basePackages = "cn.jian.controller")
@EnableWebMvc
public class SpringMvcConfig implements WebMvcConfigurer {

    @Override//放行静态资源
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /*
   视图解析器，默认跳转html
    */
    @Bean
    public InternalResourceViewResolver getViewResolver(){
        return new InternalResourceViewResolver("/WEB-INF/html",".html");
    }


    @Bean("multipartResolver")
    public CommonsMultipartResolver getMultipartResolver(){
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        return multipartResolver;
    }


    @Bean
    public LoginInteceptor loginInteceptor(){
        return new LoginInteceptor();
    }

    /**
     * 注册拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        LoginInteceptor loginInteceptor = new LoginInteceptor();
        registry.addInterceptor(loginInteceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(new String[]{"/*/login.html","/user/toLogin","/user/doLogin","/user/doRegister","/user/sendEmail","/user/changePassword"});
    }
}
