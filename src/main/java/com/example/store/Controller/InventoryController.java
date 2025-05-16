package com.example.store.Controller;

import com.example.store.DTO.InventoryDTO;
import com.example.store.DTO.InventoryTableDTO;
import com.example.store.Entity.Inventory;
import com.example.store.Entity.Product;
import com.example.store.Repository.InventoryRepo;
import com.example.store.Service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
//@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("/all")
    public Page<InventoryTableDTO> getPage(
            @PageableDefault(size = 8, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return inventoryService.getAll(pageable);
    }

    @PostMapping("/save")
    public Inventory save(@RequestBody InventoryDTO inventoryDTO){
        return inventoryService.save(inventoryDTO);
    }

    @DeleteMapping("/delete-{id}")
    public void delete(@PathVariable Long id){
        inventoryService.delete(id);
    }


}
