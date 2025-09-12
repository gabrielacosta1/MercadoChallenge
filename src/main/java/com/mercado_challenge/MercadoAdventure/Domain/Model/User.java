package com.mercado_challenge.MercadoAdventure.Domain.Model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String userId;
    private String name;
    private String email;
    private String password;
    private String address;
    private List<UserRole> userType;

}
