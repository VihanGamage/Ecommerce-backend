package com.example.store.service;

import com.example.store.dto.response.ProductResponseDto;
import com.example.store.entity.Inventory;
import com.example.store.entity.Product;
import com.example.store.entity.Review;
import com.example.store.repository.InventoryRepo;
import com.example.store.repository.ProductRepo;
import com.example.store.repository.ReviewRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor

public class ProductService {
    private final ProductRepo productRepo;
    private final InventoryRepo inventoryRepo;
    private final ReviewRepo reviewRepo;

//    public Page<Product> getAll(Pageable pageable){
//        return productRepo.findAll(pageable);
//    }

    @Cacheable(value = "products", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    public Page<ProductResponseDto> getAllProducts(Pageable pageable){
        return productRepo.findAll(pageable)
                .map(product -> new ProductResponseDto(
                        product.getId(),
                        product.getName(),
                        product.getPrice()
                ));
    }

    @CacheEvict(value = {"products","inventoryList","productPrices","reviewCounts"}, allEntries = true )
    public Product save(Product product){
        Product savedProduct = productRepo.save(product);
        Inventory inventory = new Inventory();
        inventory.setProduct(product);
        inventory.setCapacity(100);
        inventoryRepo.save(inventory);
        Review review = new Review();
        review.setProduct(product);
        reviewRepo.save(review);
        return savedProduct;
    }

    @CacheEvict(value = {"products","productPrices"}, allEntries = true)
    public Product update(Long id, Product product){
        product.setId(id);
        return productRepo.save(product);
    }

    @CacheEvict(value = "products", allEntries = true)
    @Transactional
    public void deleteById(Long id){
        inventoryRepo.deleteByProductId(id);
        productRepo.deleteById(id);
    }

    public Product getProductById(Long id){
        return productRepo.findById(id).orElse(null);
    }

}
