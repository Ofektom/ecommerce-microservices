package com.example.ecommerce.rating;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rating {
    private String id;
    private Integer productId;
    private String customerId;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
}
