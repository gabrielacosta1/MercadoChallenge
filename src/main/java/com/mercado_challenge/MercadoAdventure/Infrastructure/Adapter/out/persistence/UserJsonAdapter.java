package com.mercado_challenge.MercadoAdventure.Infrastructure.Adapter.out.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.mercado_challenge.MercadoAdventure.application.port.out.UserPersistencePort;
import com.mercado_challenge.MercadoAdventure.domain.model.User;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserJsonAdapter implements UserPersistencePort {
    private static final String JSON_FILE_PATH = "src\\main\\java\\com\\mercado_challenge\\MercadoAdventure\\Infrastructure\\Adapter\\out\\persistence\\json\\users.json";
    private final List<User> database;
    private final ObjectMapper objectMapper;

    public UserJsonAdapter() {
        this.objectMapper = new ObjectMapper();
        this.database = loadFromJsonFile();
    }

    private List<User> loadFromJsonFile() {
        File file = new File(JSON_FILE_PATH);
        if (file.exists()) {
            try {
                return objectMapper.readValue(file, new TypeReference<List<User>>() {});
            } catch (IOException e) {
                System.err.println("Error loading users from JSON file: " + e.getMessage());
                return new ArrayList<>();
            }
        }
        return new ArrayList<>();
    }

    private void writeToJsonFile() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(JSON_FILE_PATH), database);
        } catch (IOException e) {
            System.err.println("Error writing users to JSON file: " + e.getMessage());
        }
    }

    @Override
    public User save(User user) {
        if (user.getUserId() == null) {
            user.setUserId(UUID.randomUUID().toString());
            database.add(user);
        } else {
            database.removeIf(u -> u.getUserId().equals(user.getUserId()));
            database.add(user);
        }
        writeToJsonFile();
        return user;
    }

    @Override
    public Optional<User> findById(String userId) {
        return database.stream()
            .filter(user -> user.getUserId().equals(userId))
            .findFirst();
    }

    @Override
    public void deleteById(String userId) {
        database.removeIf(user -> user.getUserId().equals(userId));
        writeToJsonFile();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(database);
    }
}