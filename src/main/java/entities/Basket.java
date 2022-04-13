package entities;

import validators.BasketValidator;

import java.util.HashMap;
import java.util.Map;

public class Basket {

    private Long id;
    private final User user;
    private Map<Product, Integer> products = new HashMap<>();

    public Basket(User user) {
        this.user = user;
    }

    public Basket(Long id, User user) {
        this.id = id;
        this.user = user;
    }

    public void addProduct(Product product, int amount) {
        products.putIfAbsent(product, 0);
        products.put(product, products.get(product) + amount);
    }

    public void removeProduct(Product product) {
        new BasketValidator().validateProductInBasket(products, product);
        products.remove(product);
    }

    public void resetBasket() {
        products = new HashMap<>();
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Map<Product, Integer> getProducts() {
        return Map.copyOf(products);
    }
}
