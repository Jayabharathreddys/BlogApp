package com.jaya.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled=true, jsr250Enabled=true)
public class AppSecurityConfig{

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
/*

        http.authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/users","/users/login").permitAll()
                       // .anyRequest().authenticated()
                )
                .httpBasic(withDefaults());
*/

        http
                .authorizeHttpRequests((auth) -> auth
                                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST,"/users"))
                                        .permitAll())
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST,"/users/login"))
                        .permitAll())
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/*"))
                        .permitAll())
                .httpBasic(withDefaults())
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authz) -> authz
                        .anyRequest().authenticated());
                                //.requestMatchers("/home").hasRole("USER")
                                //.requestMatchers("/hello").hasRole("ADMIN")

        return http.build();
    }

   /* @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/users", "/ignore2");
    }*/

/*
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user1 = User.builder()
                .username("user1")
                .password(encoder().encode("123456"))
                .roles("ADMIN","USER")
                .build();
        UserDetails user2 = User.builder()
                .username("user2")
                .password(encoder().encode("123456"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user1, user2);
    }
*/

}