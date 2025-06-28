package com.example.springpracticegateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Configuration class for Spring Security in a WebFlux application.
 * This class defines the security settings for the application, including
 * authorization rules, OAuth2 resource server configuration, and CSRF settings.
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    /**
     * Defines the security filter chain for the application.
     *
     * @param http the {@link ServerHttpSecurity} object used to configure security settings.
     * @return a {@link SecurityWebFilterChain} object that contains the configured security rules.
     * <p>
     * The configuration includes:
     * - Permitting all requests to paths starting with "/oauth2/**" and "/oauth2/token".
     * - Requiring authentication for all other requests.
     * - Configuring the application as an OAuth2 resource server with JWT support.
     * - Disabling CSRF protection.
     */
    @Bean
    public SecurityWebFilterChain secFilterChain(ServerHttpSecurity http) {

        return http
                .authorizeExchange(
                        exchange -> exchange
                                .pathMatchers("/oauth2/**", "/oauth2/token").permitAll() // Allow unauthenticated access to these paths
                                .anyExchange().authenticated() // Require authentication for all other paths
                )
                .oauth2ResourceServer(
                        resourceServer -> resourceServer.jwt(Customizer.withDefaults()) // Enable JWT-based OAuth2 resource server
                )
                .csrf(
                        ServerHttpSecurity.CsrfSpec::disable // Disable CSRF protection
                )
                .build();
    }
}
