package com.example.store.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InventoryRequestDto {
    private String name;
    private int capacity;
}
