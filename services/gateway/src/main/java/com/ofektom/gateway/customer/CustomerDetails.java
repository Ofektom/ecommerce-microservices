package com.ofektom.gateway.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDetails {
    private String firstname;
    private String lastname;
    private String email;
    private Address address;
}

