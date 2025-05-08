package com.example.store.Repository;

import com.example.store.DTO.InventoryDTO;
import com.example.store.DTO.InventoryTableDTO;
import com.example.store.Entity.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InventoryRepo extends JpaRepository<Inventory,Long> {

    @Query("SELECT new com.example.store.DTO.InventoryTableDTO(i.id, p.name, i.capacity) " +
        "FROM Inventory i JOIN i.product p")
    List<InventoryTableDTO> fetchCapacityWithName();

    void deleteByProductId(Long id);

    Page<Inventory> findAll(Pageable pageable);




}
