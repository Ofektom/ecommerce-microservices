package com.ofektom.ecommerce.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
        String id,
        @NotNull(message = "User first name is required")
        String firstname,
        @NotNull(message = "User last name is required")
        String lastname,
        @Email(message = "User email is not a valid email address")
        String email,
        Address address
) {
}
