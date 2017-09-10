package com.tsystems.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.ResourceBundleViewResolver;

/**
 * Class exists for configuring web app.
 * There we set the Application's resource(css, js, font, etc.) folder,
 * viewResolver bean for mapping the app views and so on.
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.tsystems.shop")
public class WebConfig extends WebMvcConfigurerAdapter{

    /**
     * Method allow us to set app's resource folder
     * @param registry - provided parameter for setting resource handlers
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
//        localeInterceptor.setParamName("lang");
//
//
//        registry.addInterceptor(localeInterceptor);
//    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * Method register viewResolver bean in spring context.
     * It used for mapping views to strings.
     * @return ViewResolver.
     */
    @Bean(name = "viewResolver")
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        return resolver;
    }

    /**
     * Method register resourceBundleViewResolver bean in spring context
     * @return ViewResolver. It used in application to provide pdf document.
     */
    @Bean
    public ViewResolver resourceBundleViewResolver() {

        ResourceBundleViewResolver viewResolver = new ResourceBundleViewResolver();
        viewResolver.setBasename("views");
        viewResolver.setOrder(1);

        return viewResolver;
    }

    /**
     * Method register multipartResolver bean in spring context.
     * @return CommonsMultipartResolver. It used in application for uploading images
     */
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        resolver.setMaxUploadSize(100000);
        return resolver;
    }

//    @Bean(name = "messageSource")
//    public MessageSource getMessageResource()  {
//        ReloadableResourceBundleMessageSource messageResource= new ReloadableResourceBundleMessageSource();
//
//        messageResource.setBasename("classpath:/locales/messages");
//        messageResource.setDefaultEncoding("UTF-8");
//        return messageResource;
//    }
//
//    @Bean(name = "localeResolver")
//    public LocaleResolver getLocaleResolver()  {
//        CookieLocaleResolver resolver= new CookieLocaleResolver();
//        resolver.setCookieName("myAppLocaleCookie");
//        // 60 minutes
//
//        resolver.setDefaultLocale(new Locale("en"));
//        resolver.setCookieMaxAge(60*60);
//        return resolver;
//    }
}
