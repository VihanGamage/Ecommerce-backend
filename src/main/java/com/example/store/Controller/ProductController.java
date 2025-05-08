package com.example.store.Controller;

import com.example.store.Entity.Product;
import com.example.store.Repository.ProductRepo;
import com.example.store.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final ProductRepo productRepo;


    @GetMapping("/all")
    public Page<Product> getPage(
            @PageableDefault(size = 8, sort = "name", direction = Sort.Direction.ASC) Pageable pageable){
        return productService.getAll(pageable);
    }

    @GetMapping("get-{id}")
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
