package controller;

import entities.Basket;
import entities.Product;
import entities.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class WebShopController {

    private static final String PATH_OF_PRODUCTS = "src/main/resources/products.txt";

    private BasketControllerService basketService;

    private ProductControllerService productService;

    private UserControllerService userService;

    private Basket actualOrder;

    private List<Product> products;

    private InputValidator validator;

    public WebShopController(BasketControllerService basketService, ProductControllerService productService, UserControllerService userService) {
        this.basketService = basketService;
        this.productService = productService;
        this.userService = userService;
        fillProductsIntoDB();
        products = productService.createListOfProducts();
        validator = new InputValidator();
    }

    public void fillProductsIntoDB() {
        try (BufferedReader br = Files.newBufferedReader(Path.of(PATH_OF_PRODUCTS))) {
            List<Product> productsToSave = new ArrayList<>();
            String lines;
            while ((lines = br.readLine()) != null) {
                String[] parts = lines.split(";");
                productsToSave.add(new Product(parts[0], Integer.parseInt(parts[1])));
            }
            productService.insertMultipleProducts(productsToSave);
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot load products!", ioe);
        }
    }

    public boolean login(String emailAddress, String password) {
        Optional<User> user = userService.loginUser(emailAddress, password);
        if (user.isPresent()) {
            actualOrder = new Basket(user.get());
            return true;
        }
        return false;
    }


    public boolean registration(String emailAddress, String password) {
        return userService.registerUser(emailAddress, password);
    }

    public void addProductToOrder(Product product, int amount) {
        actualOrder.addProduct(product, amount);
    }

    public void removeProductFromOrder(Product product) {
        actualOrder.removeProduct(product);
    }

    public void saveOrder() {
        basketService.saveOrder(actualOrder);
    }

    public void loadProductsIntoDB() {
        fillProductsIntoDB();
        products = productService.createListOfProducts();
    }

    public List<String> getListOfFormattedProducts(String formatPattern) {
        List<String> result = new ArrayList<>();
        products.forEach(product->result.add(String.format(formatPattern, product.getName(),product.getPrice())));
        return result;
    }

    public List<String> getListOfFormattedOrderedProducts(String formatPattern) {
        List<String> result = new ArrayList<>();
        actualOrder.getProducts().forEach((k, v) -> result.add(String.format(formatPattern, k.getName(), v, v * k.getPrice())));
        return result;
    }

    public boolean isExistsOrderedProduct() {
        return !actualOrder.getProducts().isEmpty();
    }

    public Product getProductByName(String name) {
        return products.stream()
                .filter(product -> name.equals(product.getName()))
                .findFirst().get();
    }

    public boolean isUserPresent() {
        return actualOrder != null;
    }

    public String getUserEmail() {
        return actualOrder.getUser().getEmail();
    }


}
