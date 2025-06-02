package com.example.store.service;

import com.example.store.dto.response.ProductResponseDto;
import com.example.store.entity.Inventory;
import com.example.store.entity.Product;
import com.example.store.repository.InventoryRepo;
import com.example.store.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor

public class ProductService {
    private final ProductRepo productRepo;
    private final InventoryRepo inventoryRepo;

    public Page<Product> getAll(Pageable pageable){
        return productRepo.findAll(pageable);
    }

    public Page<ProductResponseDto> getAllProducts(Pageable pageable){
        return productRepo.findAll(pageable)
                .map(product -> new ProductResponseDto(
                        product.getId(),
                        product.getName(),
                        product.getPrice()
                ));
    }

    public Product save(Product product){
        Product savedProduct = productRepo.save(product);
        Inventory inventory = new Inventory();
        inventory.setProduct(product);
        inventory.setCapacity(0);
        inventoryRepo.save(inventory);
        return savedProduct;
    }

    public Product update(Long id, Product product){
        product.setId(id);
        return productRepo.save(product);
    }

    @Transactional
    public void deleteById(Long id){
        inventoryRepo.deleteByProductId(id);
        productRepo.deleteById(id);
    }

    public Product getProductById(Long id){
        return productRepo.findById(id).orElse(null);
    }

}
