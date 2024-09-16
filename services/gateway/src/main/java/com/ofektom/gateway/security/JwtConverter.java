package com.ofektom.gateway.security;

import com.ofektom.gateway.customer.Address;
import com.ofektom.gateway.customer.CustomerClient;
import com.ofektom.gateway.customer.CustomerDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    private final JwtConverterProperties properties;
    private final CustomerClient customerClient;

    public Mono<AbstractAuthenticationToken> convert(@NonNull Jwt source) {
        Collection<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(source).stream(),
                extractResourceRoles(source).stream()).collect(Collectors.toSet());

        CustomerDetails customerDetails = extractCustomerDetails(source);
        saveCustomerDetails(customerDetails);

        return Mono.just(new JwtAuthenticationToken(source, authorities, getPrincipalClaimName(source)));
    }

    private String getPrincipalClaimName(Jwt jwt) {
        String claimName = JwtClaimNames.SUB;
        if (properties.getPrincipalAttribute() != null) {
            claimName = properties.getPrincipalAttribute();
        }
        return jwt.getClaim(claimName);
    }

    @SuppressWarnings("unchecked")
    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        Map<String, Object> resource;
        Collection<String> resourceRoles;

        if (resourceAccess == null
                || (resource = (Map<String, Object>) resourceAccess.get(properties.getResourceId())) == null
                || (resourceRoles = (Collection<String>) resource.get("roles")) == null) {
            return Set.of();
        }
        return resourceRoles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
    }

    @SuppressWarnings("unchecked")
    private CustomerDetails extractCustomerDetails(Jwt jwt) {
        Map<String, Object> claims = jwt.getClaims();
        String firstName = (String) claims.getOrDefault("given_name", "");
        String lastName = (String) claims.getOrDefault("family_name", "");
        String email = (String) claims.getOrDefault("email", "");

        Object addressObj = claims.getOrDefault("address", "");
        String street = "";
        String city = "";
        String state = "";
        String zipCode = "";
        String country = "";

        if (addressObj instanceof Map) {
            Map<String, Object> address = (Map<String, Object>) addressObj;
            street = (String) address.getOrDefault("street_address", "");
            city = (String) address.getOrDefault("locality", "");
            state = (String) address.getOrDefault("region", "");
            zipCode = (String) address.getOrDefault("postal_code", "");
            country = (String) address.getOrDefault("country", "");
        } else {
            log.info("Address field is either null or not a valid Map");
        }

        Address address1 = new Address(street, city, state, zipCode, country);
        log.info("{}, {}, {}, {}", firstName, lastName, email, address1);

        return new CustomerDetails(firstName, lastName, email, address1);
    }

    private void saveCustomerDetails(CustomerDetails customerDetails) {
        customerClient.createCustomer(customerDetails);
    }
}
