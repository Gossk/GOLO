package com.golo.ecommerce.service;

import com.golo.ecommerce.dto.request.ReviewRequest;
import com.golo.ecommerce.dto.response.ReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {
    ReviewResponse createReview(ReviewRequest reviewRequest);
    ReviewResponse getReviewById(Long id);
    Page<ReviewResponse> getProductReviews(Long productId, Pageable pageable);
    List<ReviewResponse> getUserReviews();
    ReviewResponse updateReview(Long id, ReviewRequest reviewRequest);
    void deleteReview(Long id);
    boolean hasUserReviewedProduct(Long productId);
}