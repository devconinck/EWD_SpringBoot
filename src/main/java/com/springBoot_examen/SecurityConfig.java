package com.springBoot_examen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import domain.Role;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.csrfTokenRepository(new HttpSessionCsrfTokenRepository()))
                .authorizeHttpRequests(requests ->
                        requests.requestMatchers("/login**").permitAll()
                                .requestMatchers("/css/**").permitAll()
                                .requestMatchers("/events/new").hasRole("ADMIN")
                                .requestMatchers("/403**").permitAll()
                                .requestMatchers("/rest/**").permitAll()
                                .requestMatchers("/welcome/**").hasRole("USER")
                                .requestMatchers("/events/*/tickets").hasRole("USER")
                                .requestMatchers("/events/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/tickets").hasRole("USER")
                                
                                .requestMatchers("/sports/**").hasAnyRole("USER", "ADMIN"))
                .formLogin(form ->
                        form.defaultSuccessUrl("/sports", true)
                                .loginPage("/login")
                                .usernameParameter("username").passwordParameter("password")
                )
                .exceptionHandling(handling -> handling.accessDeniedPage("/403"));

        return http.build();
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)throws Exception {
    	auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
 }
