package com.example.store.repository;

import com.example.store.dto.response.AdminOrdersDto;
import com.example.store.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order,Long> {
    Page<Order> findAll(Pageable pageable);

}
