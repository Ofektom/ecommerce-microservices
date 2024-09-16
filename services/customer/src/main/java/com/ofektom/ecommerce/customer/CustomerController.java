package com.ofektom.ecommerce.customer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @PostMapping("/create")
    public ResponseEntity<String> createCustomer(
            @RequestBody @Valid CustomerRequest request
    ){
        return ResponseEntity.ok(service.createCustomer(request));
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateCustomer(
            @RequestBody @Valid CustomerRequest request
    ){
        service.updateCustomer(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<CustomerResponse>> getCustomers(){
        return ResponseEntity.ok(service.getCustomers());
    }

    @GetMapping("/exists/{customer-id}")
    public ResponseEntity<Boolean> existsById(@PathVariable("customer-id") String customerId){
        return ResponseEntity.ok(service.existsById(customerId));
    }

    @GetMapping("/{customer-id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable("customer-id") String customerId){
        return ResponseEntity.ok(service.getCustomerById(customerId));
    }

    @GetMapping("/exists/{email}")
    public ResponseEntity<Boolean> existsByEmail(@PathVariable("email") String email){
        return ResponseEntity.ok(service.existsByEmail(email));
    }

    @DeleteMapping("/delete/{customer-id}")
    public ResponseEntity<Void> delete(@PathVariable("customer-id") String customerId){
        service.deleteCustomer(customerId);
        return ResponseEntity.accepted().build();
    }
}
