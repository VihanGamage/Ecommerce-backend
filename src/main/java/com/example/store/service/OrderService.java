package com.example.store.service;

import com.example.store.dto.request.OrderRequestDto;
import com.example.store.dto.response.AdminOrdersDto;
import com.example.store.dto.response.ProductAndPriceDto;
import com.example.store.dto.response.ProductResponseDto;
import com.example.store.dto.response.UserOrdersDto;
import com.example.store.entity.*;
import com.example.store.exception.InsufficientInventoryException;
import com.example.store.repository.AppUserRepo;
import com.example.store.repository.InventoryRepo;
import com.example.store.repository.OrderRepo;
import com.example.store.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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


    @CacheEvict(value = {"userOrders","inventoryList","adminOrders"}, allEntries = true)
    @Transactional
    public Order createOrder(OrderRequestDto orderRequestDto){
        Order order = new Order();

        if (appUserRepo.existsAppUserByUserName(orderRequestDto.getName())){
            AppUser appUser = appUserRepo.findByUserName(orderRequestDto.getName());
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

    @Cacheable(value = "productPrices", key = "'page=' + #pageable.pageNumber + '&size=' + #pageable.pageSize + '&sort=' + #pageable.sort")
    public Page<ProductAndPriceDto> getProductAndPrice(Pageable pageable){
        return productRepo.findAll(pageable).map(
                product -> new ProductAndPriceDto(
                        product.getName(),
                        product.getPrice()
                )
        );
    }

    @Cacheable(value = "adminOrders", key = "#pageable.pageNumber")
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


    @CacheEvict(value = {"userOrders","adminOrders"}, allEntries = true)
    public Order updateOrderStatusByAdmin(Long id, String orderStatus){
        Order order = orderRepo.findById(id).orElseThrow(()-> new RuntimeException("Order not found"));
        OrderStatus orderStatusEnum = OrderStatus.valueOf(orderStatus);
        order.setOrderStatus(orderStatusEnum);
        return orderRepo.save(order);
    }

    @Cacheable(value = "userOrders", key = "#userName")
    public List<UserOrdersDto> getUserOrders(String userName){
        AppUser appUser = appUserRepo.findByUserName(userName);
        return orderRepo.findOrdersByAppUser(appUser).stream().map(
                order -> new UserOrdersDto(
                        order.getId(),
                        order.getProduct().getName(),
                        order.getQuantity(),
                        order.getTotal(),
                        order.getOrderStatus(),
                        order.getPlacedAt()
                )
        ).toList();
    }


    @CacheEvict(value = {"userOrders","adminOrders","inventoryList"}, allEntries = true)
    public Order cancelOrderByUser(Long id){
        Order order = orderRepo.findOrderById(id);
        order.setOrderStatus(OrderStatus.CANCELLED);
        Product product = order.getProduct();
        Inventory inventory = inventoryRepo.findByProduct(product);
        int newCapacity = order.getQuantity() + inventory.getCapacity();
        inventory.setCapacity(newCapacity);
        inventoryRepo.save(inventory);
        return orderRepo.save(order);
    }





}
