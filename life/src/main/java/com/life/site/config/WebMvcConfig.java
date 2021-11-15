/**
 * @Date    : 2019. 11. 29.
 * @package : com.hansol.config
 * @file    : WebMvcConfig.java
 * @Author  : PSJ
 * @version : 1.0
 *
 * ===============================================
 *  수정내역
 * ===============================================
 * DATE         AUTHOR         NOTE
 * -----------------------------------------------
 * 2019. 11. 29.      PSJ        최초 생성
 *
 */
package com.life.site.config;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.life.site.config.interceptor.CommonInterceptor;
import com.life.site.config.interceptor.LoginUserArgumentResolver;
import com.life.site.config.interceptor.ParamInterceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;


@Configuration
@Slf4j
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    
    @Value("${env.editorimg-path}")
    private String editorimgpath;
    
	@Value("${env.upload-path}")
    private String uploadPath;

    private final LoginUserArgumentResolver loginUserArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Parameter Check
        registry.addInterceptor(new ParamInterceptor()).addPathPatterns("/**");
        
        // Default Interceptor - 의존성 주입을 위해 객체가 아닌 Bean 생성을 통해 인터셉터 추가
        registry.addInterceptor(commonIntercepter())
                             .addPathPatterns("/**")
                             .excludePathPatterns("/*") // For index.html
                             .excludePathPatterns("/**/**") // For index.html
                             .excludePathPatterns("/css/**") // For Resources
                             .excludePathPatterns("/js/**") // For Resources
                             .excludePathPatterns("/jqGrid/**") // For Resources
                             .excludePathPatterns("/jqueryui/**") // For Resources
                             .excludePathPatterns("/img/**") // For Resources
                             .excludePathPatterns("/plugins/**") // For Resources
                             .excludePathPatterns("/html/**") // For Resources
                             /* 특정url exception 처리 */
                             .excludePathPatterns("/echo/**") // For Echo
                             .excludePathPatterns("/user/**") // For login/register/sso/logout
                             .excludePathPatterns("/nsess/**") // For popup/etc
                             .excludePathPatterns("/cert/**") // For register
                             .excludePathPatterns("/swagger-ui.html") // For Swagger
                             .excludePathPatterns("/webjars/springfox-swagger-ui/**") // For Swagger
                             .excludePathPatterns("/swagger-resources/configuration/**") // For Swagger
                             .excludePathPatterns("/v2/api-docs") // For Swagger
                             .excludePathPatterns("/logging/**") // For Statistics
                            ;
        
        registry.addInterceptor(new DeviceResolverHandlerInterceptor()).addPathPatterns("/**");;
        
        registry.addInterceptor(localeChangeInterceptor());
        
    }
   
    @Bean
    public CommonInterceptor commonIntercepter() {
        return new CommonInterceptor();
    }
    
    @Bean
    MappingJackson2JsonView jsonView(){
        return new MappingJackson2JsonView();
    }

    @Bean
    public CookieLocaleResolver localeResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setCookieName("cookie_language");
        localeResolver.setDefaultLocale(Locale.KOREA);
        return localeResolver;
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/message_resource/message");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(180);
        
        // 제공하지 않는 언어로 요청이 들어올 경우의 MessageSource 기본 언어정보.
        Locale.setDefault(Locale.KOREA);
        
        return messageSource;
    }

    @Bean
    public LocaleChangeInterceptor  localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // TODO Auto-generated method stub
        WebMvcConfigurer.super.addResourceHandlers(registry);
        registry.addResourceHandler("/editorimg/**").addResourceLocations("file:///"+editorimgpath);
        registry.addResourceHandler("/uploadTitleimg/**").addResourceLocations("file:///"+uploadPath);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        // HandlerMethodArgumentResolver 구현체를 여기에서 등록해줘야한다.
        argumentResolvers.add(loginUserArgumentResolver);
    }
}
