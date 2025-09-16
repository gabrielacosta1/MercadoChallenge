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

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        creationCommand = new ReviewCreationCommand("user1", "prod1", "Great product!", 5);

        product = new Product();
        product.setProductId("prod1");
        product.setReviews(new ArrayList<>());

        review = new Review();
        review.setReviewId("rev1");
        review.setUserId("user1");
        review.setProductId("prod1");
        review.setComment("Great product!");
        review.setRating(5);
    }

    @Test
    void testLeaveReview_whenProductExists() {
        when(productPersistencePort.findById("prod1")).thenReturn(Optional.of(product));
        when(reviewPersistencePort.save(any(Review.class))).thenReturn(review);
        when(productPersistencePort.save(any(Product.class))).thenReturn(product);

        Review result = reviewService.leaveReview(creationCommand);

        assertNotNull(result);
        assertEquals(review.getReviewId(), result.getReviewId());
        assertEquals(creationCommand.getComment(), result.getComment());
        verify(productPersistencePort, times(1)).findById("prod1");
        verify(reviewPersistencePort, times(1)).save(any(Review.class));
        verify(productPersistencePort, times(1)).save(product);
    }

    @Test
    void testLeaveReview_whenProductNotFound_thenThrowException() {
        when(productPersistencePort.findById("prod1")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            reviewService.leaveReview(creationCommand);
        });

        verify(productPersistencePort, times(1)).findById("prod1");
        verify(reviewPersistencePort, never()).save(any(Review.class));
    }

    @Test
    void testDeleteReview_whenReviewExistsAndUserIsAuthorized() {
        when(reviewPersistencePort.findById("rev1")).thenReturn(Optional.of(review));
        when(productPersistencePort.findById("prod1")).thenReturn(Optional.of(product));
        doNothing().when(reviewPersistencePort).deleteById("rev1");

        reviewService.deleteReview("rev1", "user1");

        verify(reviewPersistencePort, times(1)).findById("rev1");
        verify(productPersistencePort, times(1)).findById("prod1");
        verify(reviewPersistencePort, times(1)).deleteById("rev1");
    }

    @Test
    void testDeleteReview_whenReviewNotFound_thenThrowException() {
        when(reviewPersistencePort.findById("rev1")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            reviewService.deleteReview("rev1", "user1");
        });

        verify(reviewPersistencePort, times(1)).findById("rev1");
        verify(reviewPersistencePort, never()).deleteById(anyString());
    }

    @Test
    void testDeleteReview_whenUserNotAuthorized_thenThrowException() {
        when(reviewPersistencePort.findById("rev1")).thenReturn(Optional.of(review));

        assertThrows(SecurityException.class, () -> {
            reviewService.deleteReview("rev1", "user2");
        });

        verify(reviewPersistencePort, times(1)).findById("rev1");
        verify(reviewPersistencePort, never()).deleteById(anyString());
    }

    @Test
    void testGetReviewById_whenReviewNotFound_thenThrowException() {
        when(reviewPersistencePort.findById("rev1")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            reviewService.getReviewById("rev1");
        });

        verify(reviewPersistencePort, times(1)).findById("rev1");
    }
}
