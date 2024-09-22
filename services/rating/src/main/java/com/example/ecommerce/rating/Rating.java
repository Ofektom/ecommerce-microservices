package com.example.ecommerce.rating;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "rating")
public class Rating {
    @Id
    private String id;
    private Integer productId;
    private String customerId;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
}
