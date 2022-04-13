package service;

import entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductServiceRepository {
    void saveProduct(Product product);

    Optional<Product> findProductByName(String name);

    List<Product> getAllProducts();
}
