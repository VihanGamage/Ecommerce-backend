package com.example.store.repository;

import com.example.store.dto.response.ProductResponseDto;
import com.example.store.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product,Long> {

    Product findByName(String name);
    Page<Product> findAll(Pageable pageable);

}
