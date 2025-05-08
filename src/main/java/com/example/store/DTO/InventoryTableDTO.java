package com.example.store.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InventoryTableDTO {
    private Long id;
    private String name;
    private int capacity;
}
