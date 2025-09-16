package com.mercado_challenge.MercadoAdventure.application.port.out;

import java.util.List;
import java.util.Optional;

import com.mercado_challenge.MercadoAdventure.domain.model.User;

public interface UserPersistencePort {
    Optional <User> findById(String userId);
    User save(User user);
    List<User> findAll();
    void deleteById(String userId);
}
