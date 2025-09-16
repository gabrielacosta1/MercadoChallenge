package com.mercado_challenge.MercadoAdventure.application.port.in.command;

import java.util.List;

import com.mercado_challenge.MercadoAdventure.domain.model.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateCommand {
    private String userId;

    private String name;

    private String email;

    private String password;

    private String address;

    private List<UserRole> userType;
}
