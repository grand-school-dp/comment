package com.school.commentmanager.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    @Value("${security.admin.secret}")
    private String adminSecret;

    @Value("${security.admin.client-id}")
    private String adminClientId;

    private final RequestInterceptor requestInterceptor;

    public SecurityConfig(RequestInterceptor requestInterceptor) {
        this.requestInterceptor = requestInterceptor;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable).cors((t) -> t.configurationSource(request -> {
                    var cors = new org.springframework.web.cors.CorsConfiguration();
//                    cors.addAllowedOrigin("http://localhost:5500");
//                    cors.addAllowedOrigin("http://localhost:63342");
                    cors.addAllowedOrigin("https://grand-school.dp.ua/");
                    cors.addAllowedOrigin("https://s-grand.com.ua/");
                    cors.addAllowedHeader("*");
                    cors.addAllowedMethod("*");
                    return cors;
                }))
                .requiresChannel(channel -> channel
                        .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
                        .requiresSecure())
                .addFilterBefore(requestInterceptor, ChannelProcessingFilter.class)
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers(r -> r.getRequestURI().contains("admin")).authenticated()
                        .anyRequest().permitAll()
                )
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username(adminClientId)
                .password(adminSecret)
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
