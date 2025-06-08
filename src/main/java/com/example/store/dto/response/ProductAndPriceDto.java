package com.example.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductAndPriceDto {
    private String productName;
    private double price;
}
