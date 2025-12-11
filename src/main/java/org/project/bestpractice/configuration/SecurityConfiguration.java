package org.project.bestpractice.configuration;

import lombok.RequiredArgsConstructor;
import org.project.bestpractice.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private static final String[] WHITE_LIST_URL = {
            "/api/v1/auth/**",      // AuthEndpoints
            "/v3/api-docs/**",      // OpenAPI Json Data (Backend Data)
            "/swagger-ui/**",       // interface
            "/swagger-ui.html"      // main page
    };

    /*
                // 1. CSRF Kapat (JWT kullandığımız için statelesstır, gerek yok)
                .csrf(AbstractHttpConfigurer::disable)
                // 2. İzin Kuralları (Authorize)
                .authorizeHttpRequests(req -> req
                        .requestMatchers(WHITE_LIST_URL).permitAll()  // Listtekilere izin ver
                        .requestMatchers("/actuator/**").hasRole("ADMIN") // Actuator'a sadece ADMIN
                        .anyRequest().authenticated()                 // Geri kalan her şey için Token iste
                )
                // 3. Session Yönetimi (Stateless)
                // Spring Security'e diyoruz ki: "Sunucuda session tutma (HttpSession), her istek bağımsızdır."
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 4. Provider Tanımlaması
                .authenticationProvider(authenticationProvider)
                // 5. Filtre Sıralaması
                // UsernamePasswordAuthenticationFilter (Standart login) çalışmadan önce bizim JWT filtresi çalışsın.
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req->req
                        .requestMatchers(WHITE_LIST_URL).permitAll()
                        .requestMatchers("/actuator/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
