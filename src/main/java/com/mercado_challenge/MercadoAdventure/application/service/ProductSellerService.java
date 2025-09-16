package com.mercado_challenge.MercadoAdventure.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercado_challenge.MercadoAdventure.application.port.in.ProductSellerPort;
import com.mercado_challenge.MercadoAdventure.application.port.in.command.ProductCreationCommand;
import com.mercado_challenge.MercadoAdventure.application.port.in.command.ProductUpdateCommand;
import com.mercado_challenge.MercadoAdventure.application.port.out.ProductPersistencePort;
import com.mercado_challenge.MercadoAdventure.domain.model.Product;

@Service
public class ProductSellerService implements ProductSellerPort{

    @Autowired
    private final ProductPersistencePort productPersistencePort;

    public ProductSellerService(ProductPersistencePort productPersistencePort) {
        this.productPersistencePort = productPersistencePort;
    }

    @Override
    public Product sellProduct(ProductCreationCommand command) {
        Product newProduct = Product.createFromCommand(command);
        return productPersistencePort.save(newProduct);
    }

    @Override
    public Product updateProduct(ProductUpdateCommand command) {
        Product existingProduct = productPersistencePort.findById(command.getProductId())
            .orElseThrow(() -> new RuntimeException("Product not found with ID: " + command.getProductId()));
        existingProduct.updatedProductFromCommand(command);
        return productPersistencePort.save(existingProduct);
    }

    @Override
    public void deleteProduct(String productId, String password) {
        Optional <Product> existingProduct = productPersistencePort.findById(productId);
        if (existingProduct.isPresent()) {
            productPersistencePort.deleteById(productId);
        } else {
            throw new RuntimeException("Product not found with ID: " + productId);
        }
    }

    @Override
    public List<Product> getMyProducts(String userId, String password) {
        List<Product> products = productPersistencePort.findByUserId(userId);
        if (products.isEmpty()) {
            throw new RuntimeException("No products found for user with ID: " + userId);
        }
        return products;
    }

    @Override
    public Product  getProductById(String productId) {
        Product product = productPersistencePort.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
        return product;
    }
}
