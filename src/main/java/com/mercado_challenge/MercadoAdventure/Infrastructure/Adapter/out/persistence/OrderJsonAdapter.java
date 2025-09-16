package com.mercado_challenge.MercadoAdventure.Infrastructure.Adapter.out.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.mercado_challenge.MercadoAdventure.application.port.out.OrderPersistencePort;
import com.mercado_challenge.MercadoAdventure.domain.model.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class OrderJsonAdapter implements OrderPersistencePort {
    private static final String JSON_FILE_PATH = "src\\main\\java\\com\\mercado_challenge\\MercadoAdventure\\Infrastructure\\Adapter\\out\\persistence\\json\\orders.json";
    private final List<Order> database;
    private final ObjectMapper objectMapper;

    public OrderJsonAdapter() {
        this.objectMapper = new ObjectMapper();
        this.database = loadFromJsonFile();
    }

    private List<Order> loadFromJsonFile() {
        File file = new File(JSON_FILE_PATH);
        if (file.exists()) {
            try {
                return objectMapper.readValue(file, new TypeReference<List<Order>>() {});
            } catch (IOException e) {
                System.err.println("Error loading orders from JSON file: " + e.getMessage());
                return new ArrayList<>();
            }
        }
        return new ArrayList<>();
    }

    private void writeToJsonFile() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(JSON_FILE_PATH), database);
        } catch (IOException e) {
            System.err.println("Error writing orders to JSON file: " + e.getMessage());
        }
    }

    @Override
    public Order save(Order order) {
        if (order.getOrderId() == null) {
            order.setOrderId(UUID.randomUUID().toString());
            database.add(order);
        } else {
            database.removeIf(o -> o.getOrderId().equals(order.getOrderId()));
            database.add(order);
        }
        writeToJsonFile();
        return order;
    }

    @Override
    public Optional<Order> findById(String orderId) {
        return database.stream()
            .filter(order -> order.getOrderId().equals(orderId))
            .findFirst();
    }

    @Override
    public List<Order> findByUserId(String userId) {
        return database.stream()
            .filter(order -> order.getUserId().equals(userId))
            .toList();
    }

    @Override
    public List<Order> findAll() {
        return new ArrayList<>(database);
    }

    @Override
    public void deleteById(String orderId) {
        database.removeIf(order -> order.getOrderId().equals(orderId));
        writeToJsonFile();
    }
}