package com.example.store.Service;

import com.example.store.Entity.Product;
import com.example.store.Repository.InventoryRepo;
import com.example.store.Repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ProductService {
    private final ProductRepo productRepo;
    private final InventoryRepo inventoryRepo;

    public Page<Product> getAll(Pageable pageable){
        return productRepo.findAll(pageable);
    }

    public Product save(Product product){
        return productRepo.save(product);
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
