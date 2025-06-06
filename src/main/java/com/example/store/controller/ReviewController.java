package com.example.store.controller;

import com.example.store.dto.request.ReviewAddDto;
import com.example.store.dto.response.ReviewCountDto;
import com.example.store.dto.response.ReviewShowDto;
import com.example.store.entity.Review;
import com.example.store.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/save-{productName}")
    public Review save(@RequestBody ReviewAddDto reviewAddDto, @PathVariable String productName){
        return reviewService.saveReview(reviewAddDto,productName);
    }

    @GetMapping("/getall-{productName}")
    public List<ReviewShowDto> getReviews(@PathVariable String productName){
        return reviewService.getReviews(productName);
    }


    @GetMapping("/all")
    public Page<ReviewCountDto> getPage(
            @PageableDefault(size = 8, direction = Sort.Direction.ASC) Pageable pageable){
        return reviewService.getReviewCount(pageable);
    }
}
