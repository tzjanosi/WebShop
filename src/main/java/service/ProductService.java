package service;

import entities.Product;
import repositories.ProductRepository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class ProductService {

    private ProductRepository productRepository;

    public ProductService(DataSource dataSource) {
        this.productRepository = new ProductRepository(dataSource);
    }

    public void insertProduct(Product product) {
        if (findProductByName(product.getName()).isPresent()) {
            throw new IllegalArgumentException(product.getName() + " had already added to the database!");
        }
        productRepository.saveProduct(product);
    }

    public void insertMultipleProducts(List<Product> products) {
        products.forEach(this::insertProduct);
    }

    public Optional<Product> findProductByName(String name) {
        return productRepository.findProductByName(name);
    }
}
