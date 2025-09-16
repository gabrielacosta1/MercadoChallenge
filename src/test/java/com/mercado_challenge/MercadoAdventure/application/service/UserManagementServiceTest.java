package com.mercado_challenge.MercadoAdventure.application.service;

import com.mercado_challenge.MercadoAdventure.application.port.in.command.UserRegisterCommand;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserManagementServiceTest {

    @Mock
    private UserPersistencePort userPersistencePort;

    @InjectMocks
    private UserManagementService userManagementService;

    private UserRegisterCommand registerCommand;
    private User user;

    @BeforeEach
    void setUp() {
        registerCommand = new UserRegisterCommand(
                "Test User",
                "test@test.com",
                "password123",
                "test 1234",
                Collections.singletonList(UserRole.BUYER));

        user = new User();
        user.setUserId("1");
        user.setName(registerCommand.getName());
        user.setEmail(registerCommand.getEmail());
    }

    @Test
    void testRegisterUser() {
        // Arrange
        when(userPersistencePort.save(any(User.class))).thenReturn(user);

        // Act
        User result = userManagementService.registrerUser(registerCommand);

        // Assert
        assertNotNull(result);
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
    }
}
