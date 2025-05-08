package com.example.store.Service;

import com.example.store.DTO.InventoryDTO;
import com.example.store.DTO.InventoryTableDTO;
import com.example.store.Entity.Inventory;
import com.example.store.Entity.Product;
import com.example.store.Repository.InventoryRepo;
import com.example.store.Repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepo inventoryRepo;
    private final ProductRepo productRepo;

    public Inventory save(InventoryDTO inventoryDTO){
        Product product = productRepo.findByName(inventoryDTO.getName());
        Inventory inventory = new Inventory();
        inventory.setProduct(product);
        inventory.setCapacity(inventoryDTO.getCapacity());
        return inventoryRepo.save(inventory);
    }

    public Page<InventoryTableDTO> getAll(Pageable pageable){
        return inventoryRepo.findAll(pageable)
                .map(inventory -> new InventoryTableDTO(
                        inventory.getId(),
                        inventory.getProduct().getName(),
                        inventory.getCapacity()
                ));
    }

    public void delete(Long id){
        inventoryRepo.deleteById(id);
    }

}
