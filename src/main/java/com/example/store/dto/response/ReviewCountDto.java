package com.example.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewCountDto {
    private String productName;
    private Long reviewCount;
}
