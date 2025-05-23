package com.example.store.repository;

import com.example.store.dto.response.InventoryResponseDto;
import com.example.store.entity.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InventoryRepo extends JpaRepository<Inventory,Long> {

    @Query("SELECT new com.example.store.dto.response.InventoryResponseDto(i.id, p.name, i.capacity) " +
        "FROM Inventory i JOIN i.product p")
    List<InventoryResponseDto> fetchCapacityWithName();

    void deleteByProductId(Long id);

    Page<Inventory> findAll(Pageable pageable);




}
