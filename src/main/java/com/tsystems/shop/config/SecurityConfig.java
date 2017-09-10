package com.tsystems.shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;


/**
 * SecurityConfig class is the extension of provided by spring security
 * WebSecurityConfigAdapter. There we configure our http request rules
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@ComponentScan("com.tsystems.shop")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Service which represents the implementation of spring's UserDetailsService interface.
     * And it provide us API for loading userDetails by email. And security module decides
     * if it should authorise such user using this userDetails object
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * There we register our implementation of UserDetailsService as instrument
     * for authentication.
     * @param auth some AuthenticationManagerBuilder which spring provides us
     * @throws Exception is cases where some setting isn't correct
     */
    @Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService);
        //.passwordEncoder(getShaPasswordEncoder());
    }

    /**
     * There is overridden method where we secure our application by provided http parameter
     * @param http parameter provide us API for configuring all requests in application
     * @throws Exception in cases when we have some troubles during method processing
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/super/admin/**").access("hasRole('ROLE_SUPER_ADMIN')")
                .antMatchers("/user/**").access("hasRole('ROLE_USER')")
                .antMatchers("/account/**").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/bag/**").access("hasRole('ROLE_USER') or hasRole('ROLE_ANONYMOUS')")
                .and().formLogin().loginPage("/login").usernameParameter("email").passwordParameter("password");
        http.logout()
                .permitAll()
                .clearAuthentication(true)
                .invalidateHttpSession(false);
    }

    /**
     * Method register shaPasswordEncoder.
     * @return ShaPasswordEncoder
     */
    @Bean
    public ShaPasswordEncoder getShaPasswordEncoder(){
        return new ShaPasswordEncoder();
    }

}
