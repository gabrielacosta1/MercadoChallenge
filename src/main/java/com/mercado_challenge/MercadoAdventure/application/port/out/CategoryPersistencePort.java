package com.mercado_challenge.MercadoAdventure.application.port.out;

import java.util.List;
import java.util.Optional;

import com.mercado_challenge.MercadoAdventure.domain.model.Category;

public interface CategoryPersistencePort {
    Optional <Category> findById(String categoryId);
    Category save(Category category);
    void deleteById(String categoryId);
    List<Category> findAll();
}
