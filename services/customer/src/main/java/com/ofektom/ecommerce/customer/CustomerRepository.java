package com.ofektom.ecommerce.customer;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, String> {
    Boolean existsByEmail(String email);
    Customer findByEmail(String email);
}
