package com.mercado_challenge.MercadoAdventure.Infrastructure.Adapter.in.web;

import com.mercado_challenge.MercadoAdventure.application.port.in.ProductSellerPort;
import com.mercado_challenge.MercadoAdventure.application.port.in.command.ProductCreationCommand;
import com.mercado_challenge.MercadoAdventure.application.port.in.command.ProductUpdateCommand;
import com.mercado_challenge.MercadoAdventure.domain.model.Product;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductSellerPort productSellerPort;

    public ProductController(ProductSellerPort productSellerPort) {
        this.productSellerPort = productSellerPort;
    }

    @PostMapping
    public ResponseEntity<Product> sellProduct(@Valid @RequestBody ProductCreationCommand command) {
        Product newProduct = productSellerPort.sellProduct(command);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable String productId,
            @Valid @RequestBody ProductUpdateCommand command) {

        // Se asegura que el ID del producto sea el que se use para la actualización,
        if (command.getProductId() == null || !command.getProductId().equals(productId)) {
            command.setProductId(productId);
        }
        Product updatedProduct = productSellerPort.updateProduct(command);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String productId,
            @RequestHeader("password") String password) {
        productSellerPort.deleteProduct(productId, password);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/my-products/{userId}")
    public ResponseEntity<List<Product>> getMyProducts(@PathVariable String userId,
            @RequestHeader("password") String password) {
        List<Product> products = productSellerPort.getMyProducts(userId, password);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable String productId) {
        Product product = productSellerPort.getProductById(productId);
        return ResponseEntity.ok(product);
    }
}
