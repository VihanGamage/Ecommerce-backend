package com.example.store.controller;

import com.example.store.dto.request.OrderRequestDto;
import com.example.store.entity.Order;
import com.example.store.entity.Product;
import com.example.store.service.OrderService;
import com.example.store.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/save")
    public Order save(@RequestBody OrderRequestDto orderRequestDto){
        return orderService.createOrder(orderRequestDto);
    }

}
