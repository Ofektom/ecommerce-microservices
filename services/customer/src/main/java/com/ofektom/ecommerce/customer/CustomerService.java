package com.ofektom.ecommerce.customer;

import com.ofektom.ecommerce.exception.CustomerException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public String createCustomer(CustomerRequest request) {
        if (existsByEmail(request.email())) {
            var customer = repository.findByEmail(request.email());
            return customer.getId();
        }
        var customer = repository.save(mapper.toCustomer(request));
        return customer.getId();
    }

    public void updateCustomer(CustomerRequest request) {
        var customer = repository.findById(request.id()).orElseThrow(() -> new CustomerException(
                format("Cannot update customer:: No user found with the provided ID:: %s", request.id())
        ));
        mergeCustomer(customer, request);
        repository.save(customer);
    }

    private void mergeCustomer(Customer customer, CustomerRequest request) {
        if(StringUtils.isNotBlank(request.firstname())){
            customer.setFirstname(request.firstname());
        }
        if(StringUtils.isNotBlank(request.lastname())){
            customer.setLastname(request.lastname());
        }
        if(StringUtils.isNotBlank(request.email())){
            customer.setEmail(request.email());
        }
        if(request.address() != null){
            customer.setAddress(request.address());
        }
    }

    public List<CustomerResponse> getCustomers() {
        return repository.findAll()
                .stream()
                .map(mapper::fromCustomer)
                .collect(Collectors.toList());
    }

    public Boolean existsById(String customerId) {
        return repository.existsById(customerId);
    }

    public CustomerResponse getCustomerById(String customerId) {
        return repository.findById(customerId)
                .map(mapper::fromCustomer)
                .orElseThrow(() -> new CustomerException("Customer with ID " + customerId + " not found"));
    }

    public void deleteCustomer(String customerId) {
        repository.deleteById(customerId);
    }


    public Boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}