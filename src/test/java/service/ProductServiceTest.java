package service;

import entities.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductServiceRepository productRepository;

    @InjectMocks
    ProductService productService;

    @Test
    void createListOfProductsTest() {
        List<Product> products = List.of(new Product("bread", 300),
        new Product("milk", 250),
        new Product("sour cream", 600),
        new Product("shampoo", 1400));
        when(productRepository.getAllProducts()).thenReturn(products);
        assertThat(productService.createListOfProducts())
                .hasSize(4)
                .containsAll(products);
        verify(productRepository).getAllProducts();
    }

    @Test
    void insertProductTest() {
        Product product = new Product("milk", 250);
        productService.insertProduct(product);
        verify(productRepository).saveProduct(product);
    }

    @Test
    void insertMultipleProductsTest() {
        List<Product> products = List.of(new Product("bread", 300),
                new Product("milk", 250),
                new Product("sour cream", 600),
                new Product("shampoo", 1400));
        productService.insertMultipleProducts(products);
        verify(productRepository, times(4)).saveProduct(any(Product.class));
    }

    @Test
    void findProductByNameTest() {
        Product product = new Product("milk", 250);
        when(productRepository.findProductByName("milk")).thenReturn(Optional.of(product));
        assertThat(productService.findProductByName("milk"))
                .contains(product);
        verify(productRepository).findProductByName("milk");
    }

    @Test
    void findProductByNameNotInListTest() {
        when(productRepository.findProductByName("milk")).thenReturn(Optional.empty());
        assertThat(productService.findProductByName("milk"))
                .isEmpty();
        verify(productRepository).findProductByName("milk");
    }
}