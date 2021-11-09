package com.zghh.cinema_management.config;


import com.zghh.cinema_management.interceptor.AdminLoginInterceptor;
import com.zghh.cinema_management.interceptor.MemberLoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@EnableWebMvc
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
            registry.addViewController("/").setViewName("index");
            registry.addViewController("/index").setViewName("index");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //管理员登录拦截
        registry.addInterceptor(new AdminLoginInterceptor())
                .addPathPatterns("/addFilmPage","/allFilmPage","/updateFilmPage","/updateFilm","/deleteFilmById",
                        "/allRowPiecePage","/addRowPiecePage","/addRowPiece","/updateRowPiecePage","/updateRowPiece","/deleteRowPiece",
                        "/allScreensPage","/addScreensPage","/addScreens","/updateScreensPage","/updateScreens","/deleteScreens",
                        "/allOrderPage");
        //会员登录拦截
        registry.addInterceptor(new MemberLoginInterceptor())
                .addPathPatterns("/chooseRowPiece","/chooseSitPage","/chooseSit");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/favicon.ico")//favicon.ico
                .addResourceLocations("classpath:/static/");
    }
}
