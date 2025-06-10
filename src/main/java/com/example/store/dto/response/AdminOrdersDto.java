package com.example.store.dto.response;

import com.example.store.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AdminOrdersDto {
    private Long id;
    private String userName;
    private String productName;
    private double price;
    private int quantity;
    private BigDecimal total;
    private OrderStatus orderStatus;
    private LocalDateTime placed_at;
}
