package repositories;

import entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryTest {
    ProductRepository productRepository;

    @BeforeEach
    void init() {
        DBSource dbSource = new DBSource("/webshop.properties");
        MariaDbDataSource dataSource = dbSource.getDataSource();
        productRepository = new ProductRepository(dataSource);

        productRepository.saveProduct(new Product("Vaj",400));
        productRepository.saveProduct(new Product("WC papir",800));
        productRepository.saveProduct(new Product("Csoki",300));
        productRepository.saveProduct(new Product("Kenyér",600));
        productRepository.saveProduct(new Product("Tej",398));
    }

    @Test
    @DisplayName("insert Product into the DB and find Product test")
    void insertAndGetProductByNameTest() {
        Product product1=productRepository.findProductByName("Vaj").get();
        Product product2=productRepository.findProductByName("Kenyér").get();
        assertAll(
                () -> assertEquals("Vaj",product1.getName()),
                () -> assertEquals(400,product1.getPrice()),
                () -> assertEquals("Kenyér",product2.getName()),
                () -> assertEquals(600,product2.getPrice())
        );
    }

    @Test
    @DisplayName("insert Product into the DB and getAll Product test")
    void insertAndGetAllProductsTest() {
        List<Product> products=productRepository.getAllProducts();
        assertEquals(List.of("Tej", "Kenyér","Csoki","WC papir","Vaj"),products.stream()
                .map(Product::getName)
                .toList());
    }
}