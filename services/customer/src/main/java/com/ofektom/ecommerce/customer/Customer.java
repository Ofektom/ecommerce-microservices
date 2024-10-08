package com.ofektom.ecommerce.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "customer")
public class Customer {
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private Address address;
}
