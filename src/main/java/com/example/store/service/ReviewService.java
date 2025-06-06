package com.example.store.service;

import com.example.store.dto.request.ReviewAddDto;
import com.example.store.dto.response.ReviewCountDto;
import com.example.store.dto.response.ReviewShowDto;
import com.example.store.entity.Product;
import com.example.store.entity.Review;
import com.example.store.repository.ProductRepo;
import com.example.store.repository.ReviewRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepo reviewRepo;
    private final ProductRepo productRepo;


    public Review saveReview(ReviewAddDto reviewAddDto, String productName){
        Review review = new Review();
        review.setProduct(productRepo.findByName(productName));
        review.setReview(reviewAddDto.getReview());
        reviewRepo.save(review);
        return review;
    }

    public List<ReviewShowDto> getReviews(String productName){
        return reviewRepo.findAllByProduct(productRepo.findByName(productName));
    }

    public Page<ReviewCountDto> getReviewCount(Pageable pageable){
        return reviewRepo.findAllReviewCounts(pageable);
    }

}
