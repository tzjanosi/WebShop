package controller;

import entities.Basket;
import entities.Product;
import entities.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WebShopControllerValidatorTest {

    WebShopControllerValidator validator = new WebShopControllerValidator();

    @Test
    void testIsUserExists() {
        Optional<User> user = Optional.of(new User("pista@mail.com", "123445672"));
        assertEquals(true, validator.isUserExists(user));
    }

    @Test
    void testIsUserExistsFailed() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> validator.isUserExists(Optional.empty()));
        assertEquals("Hibás e-mail cím vagy jelszó!", exception.getMessage());
    }

    @Test
    void testOrderValidator() {
        User user = new User("pista@mail.com", "123445672");
        Basket basket = new Basket(user);
        basket.addProduct(new Product("tea", 100), 10);

        assertEquals(true, validator.orderValidator(basket));
    }

    @Test
    void testOrderValidatorWithNullUser() {
        Exception exception = assertThrows(IllegalStateException.class,
                () -> validator.orderValidator(new Basket(null)));

        assertEquals("Hiba a program működésében, vegye fel a kapcsolatot a szolgáltatóval! Tel.:123456789",
                exception.getMessage());
    }

    @Test
    void testOrderValidatorWithEmptyBasket() {
        Exception exception = assertThrows(IllegalStateException.class,
                () -> validator.orderValidator(new Basket(new User("pista@mail.com", "123445672"))));

        assertEquals("A kosár üres!", exception.getMessage());
    }

    @Test
    void testValidateEmail() {
        assertEquals(true, validator.validateEmail("a@a.a"));
    }

    @Test
    void testValidateEmailWithNull() {
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,
                () -> validator.validateEmail(null));

        assertEquals("Az e-mail null!", iae.getMessage());
    }

    @Test
    void testValidateEmailWithEmptyString() {
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,
                () -> validator.validateEmail("       "));

        assertEquals("Az e-mail üres!", iae.getMessage());
    }

    @Test
    void testValidateEmailWithFormalProblem() {
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,
                () -> validator.validateEmail("a.a@a"));

        assertEquals("Érvénytelen e-mail cím!: a.a@a", iae.getMessage());
    }

    @Test
    void testValidatePassword() {
        assertEquals(true, validator.validatePassword("1234567"));
    }

    @Test
    void testValidateTooShortPassword() {

        assertEquals("A jelszó túl rövid!", assertThrows(IllegalArgumentException.class,
                () -> validator.validatePassword("123456")).getMessage());
    }

    @Test
    void testValidatePasswordWithNull() {
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,
                () -> validator.validatePassword(null));

        assertEquals("A jelszó null!", iae.getMessage());
    }

    @Test
    void testValidatePasswordWithEmpty() {
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,
                () -> validator.validatePassword("     "));

        assertEquals("A jelszó üres!", iae.getMessage());
    }

    @Test
    void testValidateFormattedListOfOrderedProducts() {
        List<String> orderedProducts = List.of("termék 1", "termék 2");

        assertEquals(true, validator.validateFormattedListOfOrderedProducts(orderedProducts));
    }

    @Test
    void testValidateFormattedListOfOrderedProductsWithNull() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validateFormattedListOfOrderedProducts(null));

        assertEquals("A megrendelt termékek listája null.", exception.getMessage());
    }

    @Test
    void testValidateFormattedListOfOrderedProductsWithEmptyProductList() {
        List<String> productNames = new ArrayList<>();
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validateFormattedListOfOrderedProducts(productNames));

        assertEquals("A megrendelt termékek listája üres.", exception.getMessage());
    }

    @Test
    void testValidateProduct() {
        Product product = new Product("termék", 100);
        List<Product> products = List.of(product);

        assertEquals(true, validator.validateProduct(products, product));
    }

    @Test
    void testValidatorProductWithDifferentInstances() {
        Product product = new Product("termék", 100);
        List<Product> products = List.of(new Product("termék", 100));

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validateProduct(products, product));

        assertEquals("A keresett termék nincs a listában.", exception.getMessage());
    }

    @Test
    void testValidatorProductWithMissingProduct() {
        Product product = new Product("termék", 100);
        List<Product> products = List.of(new Product("tej", 100));

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validateProduct(products, product));

        assertEquals("A keresett termék nincs a listában.", exception.getMessage());
    }



}