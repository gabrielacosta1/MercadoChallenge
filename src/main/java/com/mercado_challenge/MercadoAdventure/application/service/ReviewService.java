package com.mercado_challenge.MercadoAdventure.application.service;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercado_challenge.MercadoAdventure.application.port.in.ReviewPort;
import com.mercado_challenge.MercadoAdventure.application.port.in.command.ReviewCreationCommand;
import com.mercado_challenge.MercadoAdventure.application.port.out.ProductPersistencePort;
import com.mercado_challenge.MercadoAdventure.application.port.out.ReviewPersistencePort;
import com.mercado_challenge.MercadoAdventure.domain.model.Product;
import com.mercado_challenge.MercadoAdventure.domain.model.Review;

@Service
public class ReviewService implements ReviewPort {

    @Autowired
    private ReviewPersistencePort reviewPersistencePort;

    @Autowired
    private ProductPersistencePort productPersistencePort;

    @Override
    public Review leaveReview(ReviewCreationCommand command) {
        Product product = productPersistencePort.findById(command.getProductId())
            .orElseThrow(() -> new RuntimeException("Product not found with ID: " + command.getProductId()));

        Review newReview = Review.createFromCommand(command);
        Review savedReview = reviewPersistencePort.save(newReview);

        if (product.getReviews() == null) {
            product.setReviews(new ArrayList<>());
        }
        product.getReviews().add(savedReview);
        productPersistencePort.save(product);

        return savedReview;
    }

    @Override
    public void deleteReview(String reviewId, String userId) {
        Review review = reviewPersistencePort.findById(reviewId)
            .orElseThrow(() -> new RuntimeException("Review not found with ID: " + reviewId));

        if (!review.getUserId().equals(userId)) {
            throw new SecurityException("User " + userId + " is not authorized to delete review " + reviewId);
        }

        Product product = productPersistencePort.findById(review.getProductId())
            .orElseThrow(() -> new RuntimeException("Product not found with ID: " + review.getProductId()));

        if (product.getReviews() != null) {
            product.getReviews().removeIf(r -> r.getReviewId().equals(reviewId));
            productPersistencePort.save(product);
        }

        reviewPersistencePort.deleteById(reviewId);
    }

    @Override
    public Review getReviewById(String reviewId) {
        return reviewPersistencePort.findById(reviewId)
            .orElseThrow(() -> new RuntimeException("Review not found with ID: " + reviewId));
    }
}