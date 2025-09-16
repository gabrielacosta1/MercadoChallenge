package com.mercado_challenge.MercadoAdventure.application.port.in;

import java.util.List;

import com.mercado_challenge.MercadoAdventure.application.port.in.command.ProductCreationCommand;
import com.mercado_challenge.MercadoAdventure.application.port.in.command.ProductUpdateCommand;
import com.mercado_challenge.MercadoAdventure.domain.model.Product;

public interface ProductSellerPort {
    Product sellProduct(ProductCreationCommand command);
    Product updateProduct(ProductUpdateCommand command);
    void deleteProduct(String productId, String password);
    List<Product> getMyProducts(String userId, String password);
    Product getProductById(String productId);
}
