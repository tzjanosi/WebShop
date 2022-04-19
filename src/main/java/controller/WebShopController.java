package controller;

import entities.Basket;
import entities.Product;
import entities.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WebShopController {

    private static final String PATH_OF_PRODUCTS = "src/main/resources/products.txt";

    private BasketControllerService basketService;

    private ProductControllerService productService;

    private UserControllerService userService;

    private Basket actualOrder;

    private List<Product> products;

    private WebShopControllerValidator validator;

    public WebShopController(BasketControllerService basketService, ProductControllerService productService, UserControllerService userService, WebShopControllerValidator webShopControllerValidator) {
        this.basketService = basketService;
        this.productService = productService;
        this.userService = userService;
        products = productService.createListOfProducts();
        validator = webShopControllerValidator;
        loadProductsIntoDB();
    }

    public boolean login(String email, String password) {
        validator.validateEmail(email);
        validator.validatePassword(password);
        try {
            Optional<User> user = userService.loginUser(email, password);
            validator.isUserExists(user);
            if (user.isPresent()) {
                actualOrder = new Basket(user.get());
                return true;
            }
        } catch (IllegalArgumentException iae) {
            System.out.println(iae.getMessage());
        }
        return false;
    }

    public boolean registration(String email, String password) {
        validator.validateEmail(email);
        validator.validatePassword(password);
        return userService.registerUser(email, password);
    }

    public boolean isUserPresent() {
        return actualOrder != null;
    }

    public String getUserEmail() {
        return actualOrder.getUser().getEmail();
    }

    public boolean isOrderedProductsEmpty() {
        return actualOrder.getProducts().isEmpty();
    }

    public List<String> getFormattedListOfOrderedProducts(String formatPattern) {
        List<String> result = new ArrayList<>();
        actualOrder.getProducts().forEach((k, v) -> result.add(String.format(formatPattern, k.getName(), v, v * k.getPrice())));
        validator.validateFormattedListOfOrderedProducts(result);
        return result;
    }

    public List<String> getProductNames() {
        return products.stream().map(Product::getName).toList();
    }

    public Product getProductByName(String productName) {
        return products.stream()
                .filter(product -> productName.equals(product.getName()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cannot find product!"));
    }

    public void addProductToOrder(Product product) {
        actualOrder.addProduct(product, 1);
    }

    public void addProductToOrder(Product product, int amount) {
        validator.validateProduct(products, product);
        actualOrder.addProduct(product, amount);
    }

    public List<String> getOrderedProductNames() {
        return actualOrder.getProducts().keySet().stream().map(Product::getName).toList();
    }

    public void fillProductsIntoDB() {
        try (BufferedReader br = Files.newBufferedReader(Path.of(PATH_OF_PRODUCTS))) {
            List<Product> productsToSave = readLinesFromFile(br);
            productService.insertMultipleProducts(productsToSave);
        } catch (IOException ioe) {
            System.out.println(WebShopControllerValidator.FILE_ERROR_MESSAGE);
        }
    }

    private List<Product> readLinesFromFile(BufferedReader br) throws IOException {
        List<Product> productsToSave = new ArrayList<>();
        String lines;
        while ((lines = br.readLine()) != null) {
            String[] parts = lines.split(";");
            productsToSave.add(new Product(parts[0], Integer.parseInt(parts[1])));
        }
        return productsToSave;
    }

    public void removeProductFromOrder(Product product) {
        actualOrder.removeProduct(product);
    }

    public void loadProductsIntoDB() {
        fillProductsIntoDB();
        products = productService.createListOfProducts();
    }

    public List<String> getFormattedListOfProducts(String formatPattern) {
        List<String> result = new ArrayList<>();
        products.forEach(product -> result.add(String.format(formatPattern, product.getName(), product.getPrice())));
        return result;
    }

    public void saveOrder() {
        validator.orderValidator(actualOrder);
        basketService.saveOrder(actualOrder);
        actualOrder.resetBasket();
    }
}
