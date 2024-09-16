package com.ofektom.ecommerce.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @PostMapping("/create")
    public ResponseEntity<Integer> createOrder(
            @RequestBody @Valid OrderRequest request
            ){
        return ResponseEntity.ok(service.createOrder(request));
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderResponse>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/customer/[customer-id]")
    public ResponseEntity<List<OrderResponse>> ordersByCustomerId(@PathVariable("customer-id") String customerId){
        return ResponseEntity.ok(service.findOrdersByCustomerId(customerId));
    }

    @GetMapping("/{order-id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable("order-id") Integer orderId){
        return ResponseEntity.ok(service.findById(orderId));
    }
}
