package com.example.store.controller;

import com.example.store.dto.request.OrderRequestDto;
import com.example.store.dto.response.AdminOrdersDto;
import com.example.store.dto.response.ProductAndPriceDto;
import com.example.store.dto.response.UserOrdersDto;
import com.example.store.entity.Order;
import com.example.store.entity.Product;
import com.example.store.service.OrderService;
import com.example.store.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/save")
    public Order save(@RequestBody OrderRequestDto orderRequestDto){
        return orderService.createOrder(orderRequestDto);
    }

    @GetMapping("/table")
    public Page<ProductAndPriceDto> getProductAndPrice(
            @PageableDefault(size = 8, direction = Sort.Direction.ASC) Pageable pageable){
        return orderService.getProductAndPrice(pageable);
    }

    @GetMapping("/admin-orders")
    public Page<AdminOrdersDto> getAdminOrders(
            @RequestParam(required = false) String userName,
            @PageableDefault(size = 8, direction = Sort.Direction.ASC) Pageable pageable){
        return orderService.getAdminOrders(userName, pageable);
    }

    @PatchMapping("/patch-{id}-{orderStatus}")
    public Order updateOrderStatusByAdmin(@PathVariable Long id, @PathVariable String orderStatus){
        return orderService.updateOrderStatusByAdmin(id,orderStatus);
    }

    @PatchMapping("/cancel-{id}")
    public Order cancelOrderByUser(@PathVariable Long id){
        return orderService.cancelOrderByUser(id);
    }

    @GetMapping("/user-orders")
    public List<UserOrdersDto> getUserOrders(){
        return orderService.getUserOrders();
    }


}
