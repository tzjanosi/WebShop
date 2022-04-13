package service;

import controller.ProductControllerService;
import entities.Product;

import java.util.List;
import java.util.Optional;

public class ProductService implements ProductControllerService {

    private ProductServiceRepository productServiceRepository;

    public ProductService(ProductServiceRepository productServiceRepository) {
        this.productServiceRepository = productServiceRepository;
    }

    @Override
    public List<Product> createListOfProducts() {
        return productServiceRepository.getAllProducts();
    }

    public void insertProduct(Product product) {
//        if (findProductByName(product.getName()).isPresent()) {
//            throw new IllegalArgumentException(product.getName() + " had already added to the database!");
//        }
        productServiceRepository.saveProduct(product);
    }

    @Override
    public void insertMultipleProducts(List<Product> products) {
        products.forEach(this::insertProduct);
    }

    public Optional<Product> findProductByName(String name) {
        return productServiceRepository.findProductByName(name);
    }
}
