package com.example.store.dto.response;

import com.example.store.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserOrdersDto {
    private Long id;
    private String productName;
    private int quantity;
    private String address;
    private BigDecimal total;
    private OrderStatus orderStatus;
    private LocalDateTime placed_at;
}
