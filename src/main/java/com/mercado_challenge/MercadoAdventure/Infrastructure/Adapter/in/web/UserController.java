package com.mercado_challenge.MercadoAdventure.Infrastructure.Adapter.in.web;

import com.mercado_challenge.MercadoAdventure.application.port.in.UserManagementPort;
import com.mercado_challenge.MercadoAdventure.application.port.in.command.UserRegisterCommand;
import com.mercado_challenge.MercadoAdventure.application.port.in.command.UserUpdateCommand;
import com.mercado_challenge.MercadoAdventure.domain.model.User;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserManagementPort userManagementPort;

    public UserController(UserManagementPort userManagementPort) {
        this.userManagementPort = userManagementPort;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserRegisterCommand command) {
        User newUser = userManagementPort.registrerUser(command);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable String userId, @Valid @RequestBody UserUpdateCommand command) {
        if (command.getUserId() == null || !command.getUserId().equals(userId)) {
            command.setUserId(userId);
        }
        User updatedUser = userManagementPort.updateUser(command);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        userManagementPort.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        User user = userManagementPort.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userManagementPort.getAllUsers();
        return ResponseEntity.ok(users);
    }
}

