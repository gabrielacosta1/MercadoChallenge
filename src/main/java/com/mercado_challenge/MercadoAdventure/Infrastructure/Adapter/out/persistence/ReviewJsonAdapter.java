package com.mercado_challenge.MercadoAdventure.Infrastructure.Adapter.out.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercado_challenge.MercadoAdventure.application.port.out.ReviewPersistencePort;
import com.mercado_challenge.MercadoAdventure.domain.model.Review;

@Component
public class ReviewJsonAdapter implements ReviewPersistencePort {
    private final List<Review> database = new ArrayList<>();
    private final String JSON_FILE_PATH = "src\\main\\java\\com\\mercado_challenge\\MercadoAdventure\\Infrastructure\\Adapter\\out\\persistence\\json\\reviews.json";
    private final ObjectMapper objectMapper;

    public ReviewJsonAdapter() {
        this.objectMapper = new ObjectMapper();
        this.database.addAll(loadFromJsonFile());
    }

    private List<Review> loadFromJsonFile() {
        File file = new File(JSON_FILE_PATH);
        if (file.exists()) {
            try {
                return objectMapper.readValue(file, new TypeReference<List<Review>>() {});
            } catch (IOException e) {
                System.err.println("Error loading reviews from JSON file: " + e.getMessage());
                return new ArrayList<>();
            }
        }
        return new ArrayList<>();
    }

    private void writeToJsonFile() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(JSON_FILE_PATH), database);
        } catch (IOException e) {
            System.err.println("Error writing reviews to JSON file: " + e.getMessage());
        }
    }    

    @Override
    public Review save(Review review) {
        if (review.getReviewId() == null) {
            review.setReviewId(UUID.randomUUID().toString());
            database.add(review);
        } else {
            database.removeIf(r -> r.getReviewId().equals(review.getReviewId()));
            database.add(review);
        } writeToJsonFile();
        return review;
    }

    @Override
    public void deleteById(String reviewId) {
        database.removeIf(r -> r.getReviewId().equals(reviewId));
        writeToJsonFile();
    }

    @Override
    public List<Review> findByProductId(String productId) {
        return database.stream()
            .filter(review -> review.getProductId().equals(productId))
            .toList();
    }

    @Override
    public List<Review> findByUserId(String userId) {
        return database.stream()
            .filter(review -> review.getUserId().equals(userId))
            .toList();
    }

    @Override
    public List<Review> findAll() {
        return new ArrayList<>(database);
    }

    @Override
    public Optional<Review> findById(String reviewId) {
        return database.stream()
            .filter(review -> review.getReviewId().equals(reviewId))
            .findFirst();
    }
}
