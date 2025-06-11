package com.example.store.repository;

import com.example.store.dto.response.AdminOrdersDto;
import com.example.store.dto.response.UserOrdersDto;
import com.example.store.entity.AppUser;
import com.example.store.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order,Long> {
    Page<Order> findAll(Pageable pageable);

    List<Order> findOrdersByAppUser(AppUser appUser);

    Order findOrderById(Long id);
}
