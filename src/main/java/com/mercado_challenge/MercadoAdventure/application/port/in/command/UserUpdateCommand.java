package com.mercado_challenge.MercadoAdventure.application.port.in.command;

import java.util.List;

import com.mercado_challenge.MercadoAdventure.domain.model.UserRole;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateCommand {
    @NotBlank(message = "User ID cannot be empty")
    private String userId;

    private String name;

    private String email;

    private String password;

    private String address;

    private List<UserRole> userType;
}
