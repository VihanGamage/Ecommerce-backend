package com.example.store.service;

import com.example.store.dto.request.InventoryRequestDto;
import com.example.store.dto.response.InventoryResponseDto;
import com.example.store.entity.Inventory;
import com.example.store.entity.Product;
import com.example.store.repository.InventoryRepo;
import com.example.store.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepo inventoryRepo;
    private final ProductRepo productRepo;

    public Inventory save(InventoryRequestDto inventoryRequestDTO){
        Product product = productRepo.findByName(inventoryRequestDTO.getName());
        Inventory inventory = new Inventory();
        inventory.setProduct(product);
        inventory.setCapacity(inventoryRequestDTO.getCapacity());
        return inventoryRepo.save(inventory);
    }

    public Page<InventoryResponseDto> getAll(Pageable pageable){
        return inventoryRepo.findAll(pageable)
                .map(inventory -> new InventoryResponseDto(
                        inventory.getId(),
                        inventory.getProduct().getName(),
                        inventory.getCapacity()
                ));
    }

    public InventoryResponseDto getInventoryById(Long id){
        return inventoryRepo.findById(id)
        .map(inventory -> new InventoryResponseDto(
            inventory.getId(),
            inventory.getProduct().getName(),
            inventory.getCapacity()
            )).orElse(null);
    }

    public void delete(Long id){
        inventoryRepo.deleteById(id);
    }

    public Inventory update(Long id, int capacity){
        Inventory inventory = inventoryRepo.findById(id).orElse(null);
        inventory.setCapacity(capacity);
        return inventoryRepo.save(inventory);
    }

}
