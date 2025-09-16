package com.mercado_challenge.MercadoAdventure.Infrastructure.Adapter.in.web;

import com.mercado_challenge.MercadoAdventure.application.port.in.ReviewPort;
import com.mercado_challenge.MercadoAdventure.application.port.in.command.ReviewCreationCommand;
import com.mercado_challenge.MercadoAdventure.domain.model.Review;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewPort reviewPort;

    public ReviewController(ReviewPort reviewPort) {
        this.reviewPort = reviewPort;
    }

    @PostMapping
    public ResponseEntity<Review> leaveReview(@Valid @RequestBody ReviewCreationCommand command) {
        Review newReview = reviewPort.leaveReview(command);
        return new ResponseEntity<>(newReview, HttpStatus.CREATED);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable String reviewId, @RequestParam String userId) {
        reviewPort.deleteReview(reviewId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable String reviewId) {
        Review review = reviewPort.getReviewById(reviewId);
        return ResponseEntity.ok(review);
    }
}
