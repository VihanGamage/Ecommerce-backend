package com.example.store.controller;

import com.example.store.dto.response.ProductResponseDto;
import com.example.store.entity.Product;
import com.example.store.repository.ProductRepo;
import com.example.store.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final ProductRepo productRepo;


    @GetMapping("/all")
    public Page<ProductResponseDto> getPage(
            @PageableDefault(size = 8, direction = Sort.Direction.ASC) Pageable pageable){
        return productService.getAllProducts(pageable);
    }

    @GetMapping("/get-{id}")
    public Product getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }

    @PostMapping("/save")
    public Product save(@RequestBody Product product){
        return productService.save(product);
    }

    @PutMapping("/put-{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product){
        return productService.update(id,product);
    }

    @DeleteMapping("/delete-{id}")
    public void deleteByID(@PathVariable Long id){
        productService.deleteById(id);
    }

}
