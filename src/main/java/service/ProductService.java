package service;

import controller.ProductControllerService;
import entities.Product;
import repositories.ProductRepository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class ProductService implements ProductControllerService {

    private ProductRepository productRepository;

    public ProductService(DataSource dataSource) {
        this.productRepository = new ProductRepository(dataSource);
    }

    @Override
    public List<Product> createListOfProducts() {
        return null;
    }

    public void insertProduct(Product product) {
        if (findProductByName(product.getName()).isPresent()) {
            throw new IllegalArgumentException(product.getName() + " had already added to the database!");
        }
        productRepository.saveProduct(product);
    }

    @Override
    public void insertMultipleProducts(List<Product> products) {
        products.forEach(this::insertProduct);
    }

    public Optional<Product> findProductByName(String name) {
        return productRepository.findProductByName(name);
    }
}
