package com.airbnb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

//by annotating with this becames the configuration,where  we can configure what i want to acheive with spring security
//configuration means we can configure which url which user can access.this is the file where will grant the permission
// that this user can access and this url and this user role cannot access this url
//so we will have to build a securityfilterchain  class
@Configuration
public class SecurityConfig {
    private JWTFilter jwtFilter;
    public SecurityConfig(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {
        //h(cd)2
        http.csrf().disable().cors().disable();
        //keep all http request open
        //haap
       // http.authorizeHttpRequests().anyRequest().permitAll();
        http.addFilterBefore(jwtFilter, AuthorizationFilter.class);
        http.authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/createuser","/api/v1/auth/createpropertyowner","/api/v1/auth/login","/api/v1/country/**","api/v1/city/**","/api/v1/property/**","/api/v1/auth/updateuser","/api/v1/reviews/**","/image/**","/api/v1/rooms/**","/api/v1/images/**","/swagger-ui/**")
                .permitAll()
                .requestMatchers("/api/v1/property/addproperty").hasRole("OWNER")
                .requestMatchers("/api/v1/auth/createpropertymanager").hasRole("ADMIN")
                .anyRequest().authenticated();
        return http.build();
    }
}
//    @Bean
//    public PasswordEncoder getPasswordEncoder()
//    {
//        return  new BCryptPasswordEncoder();
//    }
//i want my spring securitry framework to communicate with spirng ioc and tell let this url be an open url i dont want this url to be secure
//in order to  configure to spring securityannotate this method with bean//this will automatically create one object and in that
//object i'll automatically configure and ioc will automatically works and that object is called as HttpSecurity
//HttpSecurity object autumatically creates because of bean annotation
//CSRF Attack?