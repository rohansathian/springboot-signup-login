package com.signup.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.signup.demo.model.MyAppUserService;

import lombok.AllArgsConstructor;


@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig { 
    // Security configuration details would go here

    @Autowired
    private final MyAppUserService appUserService;
    @Autowired
    private final CustomAuthenticationSuccessHandler customSuccessHandler;

    @Bean
    public UserDetailsService userDetailsService(){
        return appUserService;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(appUserService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                 .csrf(AbstractHttpConfigurer::disable)
                 .formLogin(httpForm ->{
                     httpForm.loginPage("/login").permitAll();
                     httpForm.successHandler(customSuccessHandler);
                     httpForm.defaultSuccessUrl("/index", true);
                          
                 })
                 
                 .authorizeHttpRequests(registry ->{
                     registry.requestMatchers("/dev/**", "/req/signup", "/css/**", "/js/**", "/img/**", "/login").permitAll();
                     registry.requestMatchers("/admin/**").hasRole("ADMIN");
                     registry.anyRequest().authenticated();
                    })
                    .exceptionHandling(exception ->
                        exception.accessDeniedPage("/404")
                    ) 

                 .build();
    }
    
}
