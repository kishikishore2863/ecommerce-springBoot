package com.kishi.ecommerce.service;

import com.kishi.ecommerce.model.Product;
import com.kishi.ecommerce.model.Review;
import com.kishi.ecommerce.model.User;
import com.kishi.ecommerce.repository.ReviewRepository;
import com.kishi.ecommerce.request.CreateReviewRequest;

import java.util.List;

public interface ReviewService {
    Review createReview(CreateReviewRequest req, User user, Product product);
    List<Review>getReviewByProductId(Long productId);
    Review updateReview(Long reviewId,String reviewText, double rating,Long userId) throws Exception;
    void   deleteReview(Long reviewId, Long userId) throws Exception;

    Review getReviewById(Long reviewId) throws Exception;

}
