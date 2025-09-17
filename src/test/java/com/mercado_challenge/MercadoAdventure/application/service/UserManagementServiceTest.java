package com.mercado_challenge.MercadoAdventure.application.service;

import com.mercado_challenge.MercadoAdventure.application.port.in.command.UserRegisterCommand;
import com.mercado_challenge.MercadoAdventure.application.port.in.command.UserUpdateCommand;
import com.mercado_challenge.MercadoAdventure.application.port.out.UserPersistencePort;
import com.mercado_challenge.MercadoAdventure.domain.model.User;
import com.mercado_challenge.MercadoAdventure.domain.model.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserManagementServiceTest {

    @Mock
    private UserPersistencePort userPersistencePort;

    @InjectMocks
    private UserManagementService userManagementService;

    private UserRegisterCommand registerCommand;
    private UserUpdateCommand updateCommand;
    private User user;

    @BeforeEach
    void setUp() {
        registerCommand = new UserRegisterCommand(
                "Test User",
                "test@example.com",
                "password123",
                "123 Test St",
                Collections.singletonList(UserRole.BUYER)
        );

        user = new User();
        user.setUserId("1");
        user.setName(registerCommand.getName());
        user.setEmail(registerCommand.getEmail());

        updateCommand = new UserUpdateCommand(
                "1",
                "Updated Name",
                "updated@example.com",
                "newpass",
                "456 Updated St",
                Collections.singletonList(UserRole.SELLER)
        );
    }

    /**
     * Prueba para verificar la funcionalidad de registrar un nuevo usuario.
     */
    @Test
    void testRegisterUser() {
        // Arrange
        when(userPersistencePort.save(any(User.class))).thenReturn(user);

        // Act
        User result = userManagementService.registrerUser(registerCommand);

        // Assert
        assertNotNull(result);
        assertEquals(user.getName(), result.getName());
        verify(userPersistencePort, times(1)).save(any(User.class));
    }

    /**
     * Prueba para verificar la actualización de un usuario existente.
     */
    @Test
    void testUpdateUser_whenUserExists() {
        // Arrange
        when(userPersistencePort.findById("1")).thenReturn(Optional.of(user));
        when(userPersistencePort.save(any(User.class))).thenReturn(user);

        // Act
        User result = userManagementService.updateUser(updateCommand);

        // Assert
        assertNotNull(result);
        assertEquals(updateCommand.getName(), result.getName());
        verify(userPersistencePort, times(1)).findById("1");
        verify(userPersistencePort, times(1)).save(user);
    }

    /**
     * Prueba para verificar que se lanza una excepción al intentar actualizar un usuario que no existe.
     */
    @Test
    void testUpdateUser_whenUserNotFound_thenThrowException() {
        // Arrange
        when(userPersistencePort.findById("1")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            userManagementService.updateUser(updateCommand);
        });
        verify(userPersistencePort, times(1)).findById("1");
        verify(userPersistencePort, never()).save(any(User.class));
    }

    /**
     * Prueba para verificar la eliminación de un usuario existente.
     */
    @Test
    void testDeleteUser_whenUserExists() {
        // Arrange
        when(userPersistencePort.findById("1")).thenReturn(Optional.of(user));
        doNothing().when(userPersistencePort).deleteById("1");

        // Act
        userManagementService.deleteUser("1");

        // Assert
        verify(userPersistencePort, times(1)).findById("1");
        verify(userPersistencePort, times(1)).deleteById("1");
    }

    /**
     * Prueba para verificar que se lanza una excepción al intentar eliminar un usuario que no existe.
     */
    @Test
    void testDeleteUser_whenUserNotFound_thenThrowException() {
        // Arrange
        when(userPersistencePort.findById("1")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            userManagementService.deleteUser("1");
        });
        verify(userPersistencePort, times(1)).findById("1");
        verify(userPersistencePort, never()).deleteById(anyString());
    }

        /**
     * Prueba para verificar la recuperación de un usuario existente por ID.
     */
    @Test
    void testGetUserById_whenUserExists() {
        // Arrange
        when(userPersistencePort.findById("1")).thenReturn(Optional.of(user));

        // Act
        User result = userManagementService.getUserById("1");

        // Assert
        assertNotNull(result);
        assertEquals(user.getUserId(), result.getUserId());
        verify(userPersistencePort, times(1)).findById("1");
    }


        /**
     * Prueba para verificar que se lanza una excepción al intentar recuperar un usuario por ID que no existe.
     */
    @Test
    void testGetUserById_whenUserNotFound_thenThrowException() {
        // Arrange
        when(userPersistencePort.findById("1")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            userManagementService.getUserById("1");
        });
        verify(userPersistencePort, times(1)).findById("1");
    }
}
