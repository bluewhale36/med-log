package com.bluewhale.medlog.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())

                .authorizeHttpRequests(req -> req
                        .requestMatchers("/css/**", "/js/**", "/html/**").permitAll()
                        .requestMatchers("/appuser/login/**").permitAll()
                        .requestMatchers("/appuser/logout/**").permitAll()
                        .requestMatchers("/appuser/signin/**").permitAll()
                        .requestMatchers("/api/redis/**").permitAll()
                        .requestMatchers("/med/**").authenticated()
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/appuser/login")
                        .loginProcessingUrl("/appuser/login")
                        .defaultSuccessUrl("/", false)
                        .failureUrl("/appuser/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                )

                .logout(logout -> logout
                        .logoutUrl("/appuser/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }
}
