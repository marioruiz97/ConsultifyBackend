package com.asisge.consultifybackend.configuracion;

import com.asisge.consultifybackend.configuracion.filtros.JWTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class ConfiguracionSpringSecurity {

    private final AuthenticationProvider authenticationProvider;
    private final JWTAuthenticationFilter authenticationFilter;

    @Autowired
    public ConfiguracionSpringSecurity(AuthenticationProvider authenticationProvider, JWTAuthenticationFilter authenticationFilter) {
        this.authenticationProvider = authenticationProvider;
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        final String[] roles = {"ADMIN", "ASESOR"};
        final String apiV1Path = "/api/v1/**";

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(sessionConfigurer -> sessionConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers(HttpMethod.POST, apiV1Path).hasAnyRole(roles)
                        .requestMatchers(HttpMethod.PATCH, apiV1Path).hasAnyRole(roles)
                        .requestMatchers(HttpMethod.DELETE, apiV1Path).hasAnyRole(roles)
                        .anyRequest().authenticated());

        // config para que muestre la consola H2
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:4200");
        config.addAllowedHeader("*");
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
