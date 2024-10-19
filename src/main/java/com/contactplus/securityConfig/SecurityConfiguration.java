package com.contactplus.securityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.contactplus.services.impl.CustomUserServiceImpl;

@Configuration
public class SecurityConfiguration {

    @Autowired
    private CustomUserServiceImpl customUserServiceImpl;

    @Autowired
    private OauthenticationHandler oauthenticationHandler;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserServiceImpl);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // httpSecurity.authorizeHttpRequests(authorize -> {
        // authorize.requestMatchers("/contactplus/user/**").authenticated();
        // authorize.anyRequest().permitAll();
        // });

        httpSecurity.authorizeHttpRequests(authorize -> {
            authorize.requestMatchers("/contactplus/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });

        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        // signin using email and password using spring security

        httpSecurity.formLogin(formLogin -> {
            formLogin.loginPage("/contactplus/signin");
            formLogin.loginProcessingUrl("/do-signin");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");
            formLogin.successForwardUrl("/contactplus/user/dashboard");
            formLogin.failureUrl("/contactplus/signin?error=true");
        });

        // signout using email and password using spring security

        httpSecurity.logout(logout -> {
            logout.logoutUrl("/do-logout");
            logout.logoutSuccessUrl("/contactplus/signin?logout=true");
        });

        // signin using google oauth2

        httpSecurity.oauth2Login(oauth -> {
            oauth.loginPage("/contactplus/signin");
            oauth.successHandler(oauthenticationHandler);
        });

        return httpSecurity.build();
    }

}
