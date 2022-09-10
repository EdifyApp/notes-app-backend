package com.notes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    public FirebaseFilter firebaseFilterBean() {
        return new FirebaseFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/notes-api/swagger-ui/**").permitAll() // TODO: exclude swagger ui from auth
                .anyRequest().authenticated()

        ;

        http.addFilterBefore(firebaseFilterBean(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
