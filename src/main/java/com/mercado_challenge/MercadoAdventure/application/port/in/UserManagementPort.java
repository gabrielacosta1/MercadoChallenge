package com.mercado_challenge.MercadoAdventure.application.port.in;

import com.mercado_challenge.MercadoAdventure.application.port.in.command.UserRegisterCommand;
import com.mercado_challenge.MercadoAdventure.application.port.in.command.UserUpdateCommand;
import com.mercado_challenge.MercadoAdventure.domain.model.User;

public interface UserManagementPort {
    User registrerUser(UserRegisterCommand command);
    User updateUser(UserUpdateCommand command);
    void deleteUser(String userId);
    User getUserById(String userId);
}
