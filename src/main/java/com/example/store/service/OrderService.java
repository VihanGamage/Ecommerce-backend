package com.example.store.service;

import com.example.store.dto.request.OrderRequestDto;
import com.example.store.dto.response.AdminOrdersDto;
import com.example.store.dto.response.ProductAndPriceDto;
import com.example.store.dto.response.ProductResponseDto;
import com.example.store.entity.*;
import com.example.store.exception.InsufficientInventoryException;
import com.example.store.repository.AppUserRepo;
import com.example.store.repository.InventoryRepo;
import com.example.store.repository.OrderRepo;
import com.example.store.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;
    private final ProductRepo productRepo;
    private final AppUserRepo appUserRepo;
    private final InventoryRepo inventoryRepo;

    @Transactional
    public Order createOrder(OrderRequestDto orderRequestDto){
        Order order = new Order();

        if (appUserRepo.existsAppUserByUserName(orderRequestDto.getName())){
            AppUser appUser = appUserRepo.findByUserName(orderRequestDto.getName());
            appUserRepo.save(appUser);
            order.setAppUser(appUser);
        }else {
            AppUser appUser = new AppUser();
            appUser.setUserName(orderRequestDto.getName());
            appUser.setAddress(orderRequestDto.getAddress());
            appUserRepo.save(appUser);
            order.setAppUser(appUser);
        }

        Product product = productRepo.findByName(orderRequestDto.getProductName());
        order.setProduct(product);
        order.setQuantity(orderRequestDto.getQuantity());

        Inventory inventory = inventoryRepo.findByProduct(product);
        int newCapacity = inventory.getCapacity()- orderRequestDto.getQuantity();
        if (newCapacity>=0){
            inventory.setCapacity(newCapacity);

            double productPrice = product.getPrice();
            order.setTotal(BigDecimal.valueOf(productPrice* orderRequestDto.getQuantity()));
            order.setOrderStatus(OrderStatus.PENDING);
            order.setPlacedAt(LocalDateTime.now());
            return orderRepo.save(order);
        }else {
            throw new InsufficientInventoryException("Not enough stock " +
                    "for product: "+product.getName());
        }

    }

    public Page<ProductAndPriceDto> getProductAndPrice(Pageable pageable){
        return productRepo.findAll(pageable).map(
                product -> new ProductAndPriceDto(
                        product.getName(),
                        product.getPrice()
                )
        );
    }

    public Page<AdminOrdersDto> getAdminOrders(Pageable pageable){
        return orderRepo.findAll(pageable).map(
                order -> new AdminOrdersDto(
                        order.getId(),
                        order.getAppUser().getUserName(),
                        order.getProduct().getName(),
                        order.getProduct().getPrice(),
                        order.getQuantity(),
                        order.getTotal(),
                        order.getOrderStatus(),
                        order.getPlacedAt()
                )
        );
    }

    public Order updateOrderStatusByAdmin(Long id, String orderStatus){
        Order order = orderRepo.findById(id).orElseThrow(()-> new RuntimeException("Order not found"));
        OrderStatus orderStatusEnum = OrderStatus.valueOf(orderStatus);
        order.setOrderStatus(orderStatusEnum);
        return orderRepo.save(order);
    }


}
