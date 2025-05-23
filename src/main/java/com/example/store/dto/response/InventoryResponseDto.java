package com.example.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InventoryResponseDto {
    private Long id;
    private String name;
    private int capacity;
}
