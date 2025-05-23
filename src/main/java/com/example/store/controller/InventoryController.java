package com.example.store.controller;

import com.example.store.dto.request.InventoryRequestDto;
import com.example.store.dto.response.InventoryResponseDto;
import com.example.store.entity.Inventory;
import com.example.store.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("/all")
    public Page<InventoryResponseDto> getPage(
            @PageableDefault(size = 8, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return inventoryService.getAll(pageable);
    }

    @PostMapping("/save")
    public Inventory save(@RequestBody InventoryRequestDto inventoryRequestDTO){
        return inventoryService.save(inventoryRequestDTO);
    }

    @DeleteMapping("/delete-{id}")
    public void delete(@PathVariable Long id){
        inventoryService.delete(id);
    }


}
