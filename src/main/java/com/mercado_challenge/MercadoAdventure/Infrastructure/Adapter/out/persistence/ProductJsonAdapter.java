package com.mercado_challenge.MercadoAdventure.Infrastructure.Adapter.out.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercado_challenge.MercadoAdventure.application.port.out.ProductPersistencePort;
import com.mercado_challenge.MercadoAdventure.domain.model.Product;

@Component
public class ProductJsonAdapter implements ProductPersistencePort {
    private final List<Product> database = new ArrayList<>();
    private final String JSON_FILE_PATH = "src\\main\\java\\com\\mercado_challenge\\MercadoAdventure\\Infrastructure\\Adapter\\out\\persistence\\json\\products.json";
    private final ObjectMapper objectMapper;

    public ProductJsonAdapter() {
        this.objectMapper = new ObjectMapper();
        this.database.addAll(loadFromJsonFile());
    }

    private List<Product> loadFromJsonFile() {
        File file = new File(JSON_FILE_PATH);
        if (file.exists()) {
            try {
                return objectMapper.readValue(file, new TypeReference<List<Product>>() {});
            } catch (IOException e) {
                System.err.println("Error loading products from JSON file: " + e.getMessage());
                return new ArrayList<>();
            }
        }
        return new ArrayList<>();
    }

    private void writeToJsonFile() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(JSON_FILE_PATH), database);
        } catch (IOException e) {
            System.err.println("Error writing products to JSON file: " + e.getMessage());
        }
    }


    @Override
    public Product save(Product product) {
        if (product.getProductId() == null) {
            product.setProductId(UUID.randomUUID().toString());
            database.add(product);
        } else {
            // Elimino el viejo y agrego el actualizado
            database.removeIf(p -> p.getProductId().equals(product.getProductId()));
            database.add(product);
        }
        writeToJsonFile();
        return product;
    }

    @Override
    public Optional<Product> findById(String productId) {
        return database.stream()
            .filter(product -> product.getProductId().equals(productId))
            .findFirst();
    }

    @Override
    public void deleteById(String productId) {
        database.removeIf(product -> product.getProductId().equals(productId));
        writeToJsonFile();
    }

    @Override
    public List<Product> findByUserId(String userId) {
        return database.stream()
            .filter(product -> product.getUserId().equals(userId))
            .collect(Collectors.toList());
    }

    @Override
    public List<Product> findAll() {
        return database.stream()
            .collect(Collectors.toList());
    }

    
}
