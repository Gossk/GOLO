package com.golo.ecommerce.controller;

import com.golo.ecommerce.dto.request.ReviewRequest;
import com.golo.ecommerce.dto.response.ApiResponse;
import com.golo.ecommerce.dto.response.ReviewResponse;
import com.golo.ecommerce.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> createReview(@Valid @RequestBody ReviewRequest reviewRequest) {
        ReviewResponse review = reviewService.createReview(reviewRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "Review created successfully", review));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReviewById(@PathVariable Long id) {
        ReviewResponse review = reviewService.getReviewById(id);
        return ResponseEntity.ok(new ApiResponse(true, "Review retrieved successfully", review));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getProductReviews(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<ReviewResponse> reviews = reviewService.getProductReviews(productId, pageable);
        return ResponseEntity.ok(new ApiResponse(true, "Product reviews retrieved successfully", reviews));
    }

    @GetMapping("/my-reviews")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> getUserReviews() {
        List<ReviewResponse> reviews = reviewService.getUserReviews();
        return ResponseEntity.ok(new ApiResponse(true, "User reviews retrieved successfully", reviews));
    }

    @GetMapping("/product/{productId}/can-review")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> canUserReviewProduct(@PathVariable Long productId) {
        boolean hasReviewed = reviewService.hasUserReviewedProduct(productId);
        return ResponseEntity.ok(new ApiResponse(true, "Check completed", !hasReviewed));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> updateReview(
            @PathVariable Long id,
            @Valid @RequestBody ReviewRequest reviewRequest
    ) {
        ReviewResponse review = reviewService.updateReview(id, reviewRequest);
        return ResponseEntity.ok(new ApiResponse(true, "Review updated successfully", review));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok(new ApiResponse(true, "Review deleted successfully"));
    }
}