package com.jaya.security;

import com.jaya.users.UsersService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled=true, jsr250Enabled=true)
//@RequiredArgsConstructor
public class AppSecurityConfig{

    private final JWTAuthenticationFilter jwtAuthenticationFilter;

    private final UsersService usersService;
    public AppSecurityConfig(UsersService usersService) {
        this.usersService = usersService;
        JWTService jwtService = new JWTService();
        this.jwtAuthenticationFilter = new JWTAuthenticationFilter(
                new JWTAuthenticationManager(jwtService, this.usersService)
        );
    }

    @Bean
    public static PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((auth) -> auth
                                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST,"/users/login"))
                                        .permitAll())
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST,"/users/signup"))
                        .permitAll())
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/articles/*"))
                        .permitAll())
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST,"/articles/*"))
                        .permitAll())
                .httpBasic(withDefaults())
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) -> auth
                        .anyRequest().authenticated());
            http.addFilterBefore(jwtAuthenticationFilter, AnonymousAuthenticationFilter.class);
        return http.build();
    }


}