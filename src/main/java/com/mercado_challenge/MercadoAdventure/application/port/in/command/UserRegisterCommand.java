package com.mercado_challenge.MercadoAdventure.application.port.in.command;

import java.util.List;

import com.mercado_challenge.MercadoAdventure.domain.model.UserRole;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterCommand {
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Email cannot be empty")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    private String password;

    @NotBlank(message = "Address cannot be empty")
    private String address;

    @NotEmpty(message = "User type cannot be empty")
    private List<UserRole> userType;
    
}
