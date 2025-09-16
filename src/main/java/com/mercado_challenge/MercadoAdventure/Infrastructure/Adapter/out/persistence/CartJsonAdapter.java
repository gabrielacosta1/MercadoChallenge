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
import com.mercado_challenge.MercadoAdventure.application.port.out.CartPersistencePort;
import com.mercado_challenge.MercadoAdventure.domain.model.Cart;

@Component
public class CartJsonAdapter implements CartPersistencePort {
    private final List<Cart> database = new ArrayList<>();
    private static final String JSON_FILE_PATH = "src\\main\\java\\com\\mercado_challenge\\MercadoAdventure\\Infrastructure\\Adapter\\out\\persistence\\json\\carts.json";
    private final ObjectMapper objectMapper;


    public CartJsonAdapter() {
        this.objectMapper = new ObjectMapper();
        this.database.addAll(loadFromJsonFile());
    }

    private List<Cart> loadFromJsonFile() {
        File file = new File(JSON_FILE_PATH);
        if (file.exists()) {
            try {
                return objectMapper.readValue(file, new TypeReference<List<Cart>>() {});
            } catch (IOException e) {
                System.err.println("Error loading carts from JSON file: " + e.getMessage());
                return new ArrayList<>();
            }
        }
        return new ArrayList<>();
    }

    private void writeToJsonFile() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(JSON_FILE_PATH), database);
        } catch (IOException e) {
            System.err.println("Error writing carts to JSON file: " + e.getMessage());
        }
    }


    @Override
    public Optional<Cart> findById(String cartId) {
        return database.stream()
            .filter(cart -> cart.getCartId().equals(cartId))
            .findFirst();
    }

    @Override
    public void addProductToCart(Cart cart) {
        save(cart);
    }

    @Override
    public void removeProductFromCart(String cartId, String productId) {
        database.stream()
            .filter(cart -> cart.getCartId().equals(cartId))
            .findFirst()
            .ifPresent(cart -> cart.getItems().removeIf(item -> item.getProductId().equals(productId)));
            writeToJsonFile();
        }

    @Override
    public Cart save(Cart cart) {
        if (cart.getCartId() == null) {
            cart.setCartId(UUID.randomUUID().toString());
            database.add(cart);
        } else {
            database.removeIf(c -> c.getCartId().equals(cart.getCartId()));
            database.add(cart);
        }
        writeToJsonFile();
        return cart;
    }

    @Override
    public void deleteCart(Cart cart) {
        database.removeIf(c -> c.getCartId().equals(cart.getCartId()));
        writeToJsonFile();
    }

    @Override
    public Optional<Cart> findByUserId(String userId) {
        return database.stream()
            .filter(cart -> cart.getUserId().equals(userId))
            .findFirst();
    }

    @Override
    public void clearCart(String userId) {
        database.stream()
            .filter(cart -> cart.getUserId().equals(userId))
            .findFirst()
            .ifPresent(cart -> cart.getItems().clear());
        writeToJsonFile();
    }

    @Override
    public List<Cart> findAll() {
        return new ArrayList<>(database);
    }
    
}
