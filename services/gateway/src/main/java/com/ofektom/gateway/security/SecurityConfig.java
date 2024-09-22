package com.ofektom.gateway.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtConverter jwtConverter;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity){
        serverHttpSecurity
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/", "/eureka/**", "/api/v1/customers/create").permitAll()
                        .pathMatchers("/api/v1/products/create").hasAnyRole("ADMIN")
                        .pathMatchers("/api/v1/products/all").hasAnyRole("CUSTOMER", "ADMIN")
                        .pathMatchers("/api/v1/products/{product-id}").hasAnyRole("CUSTOMER", "ADMIN")
                        .pathMatchers("/api/v1/customers/delete/{customer-id}").hasRole("ADMIN")
                        .pathMatchers("/api/v1/customers/{customer-id}").hasAnyRole("ADMIN", "CUSTOMER")
                        .pathMatchers("/api/v1/customers/update").hasAnyRole("ADMIN", "CUSTOMER")
                        .pathMatchers("/api/v1/customers/all").hasRole("ADMIN")
                        .pathMatchers("/api/v1/orders/create").hasAnyRole("CUSTOMER", "ADMIN")
                        .pathMatchers("/api/v1/orders/{order-id}").hasAnyRole("CUSTOMER", "ADMIN")
                        .pathMatchers("/api/v1/orders/customer/{customer-id}").hasAnyRole("CUSTOMER", "ADMIN")
                        .pathMatchers("/api/v1/orders/all").hasRole("ADMIN")
                        .pathMatchers("/api/v1/payments/all", "/api/v1/ratings/all").hasRole("ADMIN")
                        .pathMatchers("/api/v1/order-lines/**").hasAnyRole("CUSTOMER", "ADMIN")
                        .pathMatchers("/api/v1/ratings/add", "/api/v1/recommenders/recommendation").hasRole("CUSTOMER")
                        .pathMatchers("/api/v1/ratings/product/{productId}", "api/v1/ratings/product/{productId}/average").hasRole("ADMIN")
                        .anyExchange().authenticated())
//                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtConverter)));
        return serverHttpSecurity.build();
    }
}
