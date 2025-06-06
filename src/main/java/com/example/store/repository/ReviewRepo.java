package com.example.store.repository;

import com.example.store.dto.response.ReviewCountDto;
import com.example.store.dto.response.ReviewShowDto;
import com.example.store.entity.Inventory;
import com.example.store.entity.Product;
import com.example.store.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepo extends JpaRepository<Review,Long> {

    @Query("SELECT new com.example.store.dto.response.ReviewCountDto(r.product.name, COUNT(r)) " +
    "FROM Review r GROUP BY r.product.name")
    Page<ReviewCountDto> findAllReviewCounts(Pageable pageable);

    List<ReviewShowDto> findAllByProduct(Product product);

}
