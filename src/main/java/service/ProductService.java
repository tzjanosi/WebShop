package service;

import entities.Product;
import repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void insertProduct(Product product) {
        if (findProductByName(product.getName()).isPresent()) {
            throw new IllegalArgumentException(product.getName() + " had already added to the database!");
        }
        productRepository.insertProduct(product);
    }

    public void insertMultipleProducts(List<Product> products) {
        products.forEach(this::insertProduct);
    }

    public Optional<Product> findProductByName(String name) {
        return productRepository.findProductByName(name);
    }
}
