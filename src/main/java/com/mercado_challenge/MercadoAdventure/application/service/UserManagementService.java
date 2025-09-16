package com.mercado_challenge.MercadoAdventure.application.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.mercado_challenge.MercadoAdventure.application.port.in.UserManagementPort;
import com.mercado_challenge.MercadoAdventure.application.port.in.command.UserRegisterCommand;
import com.mercado_challenge.MercadoAdventure.application.port.in.command.UserUpdateCommand;
import com.mercado_challenge.MercadoAdventure.application.port.out.UserPersistencePort;
import com.mercado_challenge.MercadoAdventure.domain.model.User;

public class UserManagementService implements UserManagementPort {
    @Autowired
    private UserPersistencePort userPersistencePort;

    @Override
    public User registrerUser(UserRegisterCommand command) {
        User newUser = User.createFromCommand(command);
        return userPersistencePort.save(newUser);
    }

    @Override
    public User updateUser(UserUpdateCommand command) {
        User existingUser = userPersistencePort.findById(command.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found with ID: " + command.getUserId()));
        existingUser = User.updateUserFromCommand(command);
        return userPersistencePort.save(existingUser);
    }

    @Override
    public void deleteUser(String userId) {
        Optional <User> existingUser = userPersistencePort.findById(userId);
        if (existingUser.isPresent()) {
            userPersistencePort.deleteById(userId);
        } else {
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }

    @Override
    public User getUserById(String userId) {
        return userPersistencePort.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }

    
}
