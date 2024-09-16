package com.ofektom.ecommerce.order;

import com.ofektom.ecommerce.customer.CustomerClient;
import com.ofektom.ecommerce.exception.BusinessException;
import com.ofektom.ecommerce.kafka.OrderConfirmation;
import com.ofektom.ecommerce.kafka.OrderProducer;
import com.ofektom.ecommerce.orderline.OrderLineRequest;
import com.ofektom.ecommerce.orderline.OrderLineService;
import com.ofektom.ecommerce.payment.PaymentClient;
import com.ofektom.ecommerce.payment.PaymentRequest;
import com.ofektom.ecommerce.product.ProductClient;
import com.ofektom.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public Integer createOrder(OrderRequest request) {
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No customer exists with the provided ID"));

        var purchasedProducts = this.productClient.purchaseProducts(request.products());
        var order = repository.save(mapper.toOrder(request));
        for(PurchaseRequest purchaseRequest : request.products()){
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        try{
            paymentClient.requestOrderPayment(paymentRequest);
        } catch (Exception e) {
        throw new BusinessException("Payment request failed: " + e.getMessage());
    }


        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts

                )
        );
        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {
        return repository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(format("No order found with the ID:: " + orderId)));
    }

    public List<OrderResponse> findOrdersByCustomerId(String customerId) {
        return repository.findOrdersByCustomerId(customerId)
                .stream()
                .map(mapper::fromOrder)
                .collect(Collectors.toList());
    }
}
