package com.mercado_challenge.MercadoAdventure.application.port.out;

import java.util.List;
import java.util.Optional;

import com.mercado_challenge.MercadoAdventure.domain.model.Review;

public interface ReviewPersistencePort {
    Optional <Review> findById(String reviewId);
    Review save(Review review);
    void deleteById(String reviewId);
    List<Review> findByProductId(String productId);
    List<Review> findByUserId(String userId);
    List<Review> findAll();
}
