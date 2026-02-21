package com.golo.ecommerce.service.impl;

import com.golo.ecommerce.dto.request.ReviewRequest;
import com.golo.ecommerce.dto.response.ReviewResponse;
import com.golo.ecommerce.entity.Product;
import com.golo.ecommerce.entity.Review;
import com.golo.ecommerce.entity.User;
import com.golo.ecommerce.exception.BadRequestException;
import com.golo.ecommerce.exception.ResourceNotFoundException;
import com.golo.ecommerce.repository.OrderRepository;
import com.golo.ecommerce.repository.ProductRepository;
import com.golo.ecommerce.repository.ReviewRepository;
import com.golo.ecommerce.service.ReviewService;
import com.golo.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Override
    public ReviewResponse createReview(ReviewRequest reviewRequest) {
        User currentUser = userService.getCurrentUser();

        Product product = productRepository.findById(reviewRequest.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", reviewRequest.getProductId()));

        // Check if user already reviewed this product
        if (reviewRepository.existsByProductIdAndUserId(product.getId(), currentUser.getId())) {
            throw new BadRequestException("You have already reviewed this product");
        }

        // Check if user purchased this product
        Long orderCount = orderRepository.countOrdersByUserId(currentUser.getId());
        boolean hasPurchased = orderCount > 0;

        Review review = new Review();
        review.setProduct(product);
        review.setUser(currentUser);
        review.setRating(reviewRequest.getRating());
        review.setComment(reviewRequest.getComment());
        review.setVerified(hasPurchased);

        Review savedReview = reviewRepository.save(review);

        // Update product rating
        updateProductRating(product.getId());

        return mapToResponse(savedReview);
    }

    @Override
    public ReviewResponse getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", id));
        return mapToResponse(review);
    }

    @Override
    public Page<ReviewResponse> getProductReviews(Long productId, Pageable pageable) {
        return reviewRepository.findByProductId(productId, pageable)
                .map(this::mapToResponse);
    }

    @Override
    public List<ReviewResponse> getUserReviews() {
        User currentUser = userService.getCurrentUser();
        return reviewRepository.findByUserId(currentUser.getId())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewResponse updateReview(Long id, ReviewRequest reviewRequest) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", id));

        User currentUser = userService.getCurrentUser();
        if (!review.getUser().getId().equals(currentUser.getId())) {
            throw new BadRequestException("Access denied");
        }

        review.setRating(reviewRequest.getRating());
        review.setComment(reviewRequest.getComment());

        Review updatedReview = reviewRepository.save(review);

        // Update product rating
        updateProductRating(review.getProduct().getId());

        return mapToResponse(updatedReview);
    }

    @Override
    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", id));

        User currentUser = userService.getCurrentUser();
        if (!review.getUser().getId().equals(currentUser.getId()) &&
                !currentUser.getRole().equals(User.Role.ADMIN)) {
            throw new BadRequestException("Access denied");
        }

        Long productId = review.getProduct().getId();
        reviewRepository.delete(review);

        // Update product rating
        updateProductRating(productId);
    }

    @Override
    public boolean hasUserReviewedProduct(Long productId) {
        User currentUser = userService.getCurrentUser();
        return reviewRepository.existsByProductIdAndUserId(productId, currentUser.getId());
    }

    private void updateProductRating(Long productId) {
        Double averageRating = reviewRepository.calculateAverageRating(productId);
        Integer reviewCount = reviewRepository.countByProductId(productId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        product.setRating(averageRating != null ? averageRating : 0.0);
        product.setReviewCount(reviewCount);
        productRepository.save(product);
    }

    private ReviewResponse mapToResponse(Review review) {
        ReviewResponse response = new ReviewResponse();
        response.setId(review.getId());
        response.setProductId(review.getProduct().getId());
        response.setProductName(review.getProduct().getName());
        response.setUserId(review.getUser().getId());
        response.setUserName(review.getUser().getFirstName() + " " + review.getUser().getLastName());
        response.setRating(review.getRating());
        response.setComment(review.getComment());
        response.setVerified(review.getVerified());
        response.setCreatedAt(review.getCreatedAt());
        return response;
    }
}