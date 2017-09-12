package com.tsystems.shop.config;


import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Application initializer which registers dispatcher servlet
 * and some other important settings.
 * It also registers our custom configs for this web application.
 * For example, WebConfig {@link WebConfig}
 *              SecurityConfig {@link SecurityConfig}
 *              JmsConfig {@link JmsConfig}
 */
public class ApplicationInitializer implements WebApplicationInitializer{
    /**
     * The name of our dispatcher servlet
     */
    private static final String DISPATCHER = "dispatcher";

    /**
     * Method we had to implement for correct working of out application.
     * In Method we set all settings. See description of the class above.
     * @param servletContext - context of the application
     * @throws ServletException in some cases when our settings isn't correct
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(WebConfig.class);
        ctx.register(SecurityConfig.class);
        ctx.register(JmsConfig.class);
        servletContext.addListener(new ContextLoaderListener(ctx));

        ServletRegistration.Dynamic servlet = servletContext.addServlet(DISPATCHER, new DispatcherServlet(ctx));
        servlet.setAsyncSupported(true);
        servlet.addMapping("/");
        servlet.setLoadOnStartup(1);

        FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encoding-filter", CharacterEncodingFilter.class);
        encodingFilter.setInitParameter("encoding", "UTF-8");
        encodingFilter.setInitParameter("forceEncoding", "true");
        encodingFilter.addMappingForUrlPatterns(null, true, "/*");
    }


}
