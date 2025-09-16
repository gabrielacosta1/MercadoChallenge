package com.mercado_challenge.MercadoAdventure.application.service;

import com.mercado_challenge.MercadoAdventure.application.port.in.command.ReviewCreationCommand;
import com.mercado_challenge.MercadoAdventure.application.port.out.ProductPersistencePort;
import com.mercado_challenge.MercadoAdventure.application.port.out.ReviewPersistencePort;
import com.mercado_challenge.MercadoAdventure.domain.model.Product;
import com.mercado_challenge.MercadoAdventure.domain.model.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewPersistencePort reviewPersistencePort;

    @Mock
    private ProductPersistencePort productPersistencePort;

    @InjectMocks
    private ReviewService reviewService;

    private ReviewCreationCommand creationCommand;
    private Product product;
    private Review review;

    @BeforeEach
    void setUp() {
        creationCommand = new ReviewCreationCommand("user1", "prod1", "Probando producto", 5);

        product = new Product();
        product.setProductId("prod1");

        review = new Review();
        review.setReviewId("rev1");
        review.setUserId("user1");
        review.setProductId("prod1");
        review.setComment("Probando producto");
        review.setRating(5);
    }

    @Test
    void testLeaveReview() {
        // Arrange
        when(productPersistencePort.findById("prod1")).thenReturn(Optional.of(product));
        when(reviewPersistencePort.save(any(Review.class))).thenReturn(review);
        when(productPersistencePort.save(any(Product.class))).thenReturn(product);

        // Act
        Review result = reviewService.leaveReview(creationCommand);

        // Assert
        assertNotNull(result);
        assertEquals(review.getReviewId(), result.getReviewId());
        assertEquals(creationCommand.getComment(), result.getComment());
    }
}
