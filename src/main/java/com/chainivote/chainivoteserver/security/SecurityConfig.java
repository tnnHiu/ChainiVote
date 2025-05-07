package com.chainivote.chainivoteserver.security;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthEntryPoint jwtAuthEntryPoint;

    private CustomUserDetailService userDetailsService;

    @Autowired
    public SecurityConfig(JwtAuthEntryPoint jwtAuthEntryPoint, CustomUserDetailService userDetailsService) {
        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HttpSession httpSession) throws Exception {
        return http.cors(cors -> cors.configurationSource(corsConfigurationSource())).csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers("/").permitAll().requestMatchers("/api/auth/**").permitAll().requestMatchers(HttpMethod.GET, "/api/poll/").permitAll().requestMatchers(HttpMethod.GET, "/api/poll/get-all-without-candidate").permitAll().requestMatchers(HttpMethod.GET, "/api/poll/get-all-by-category/{categoryId}").permitAll().requestMatchers(HttpMethod.GET, "/api/poll/{pollId}").permitAll().requestMatchers(HttpMethod.GET, "/api/candidate/**").permitAll().requestMatchers(HttpMethod.GET, "/api/category/**").permitAll().anyRequest().authenticated()).addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class).exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthEntryPoint)).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}