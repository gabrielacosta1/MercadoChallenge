package com.mercado_challenge.MercadoAdventure.application.port.in;

import com.mercado_challenge.MercadoAdventure.application.port.in.command.ReviewCreationCommand;
import com.mercado_challenge.MercadoAdventure.domain.model.Review;

public interface ReviewPort {
   Review leaveReview(ReviewCreationCommand command);
   void deleteReview(String reviewId, String userId);
   Review getReviewById(String reviewId);
}
