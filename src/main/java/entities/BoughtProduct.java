package entities;

import validators.BoughtProductValidator;

import java.util.HashMap;
import java.util.Map;

public class BoughtProduct {

    private final Long id;
    private final User user;
    private Map<Product, Integer> products = new HashMap<>();

    public BoughtProduct(Long id, User user) {
        this.id = id;
        this.user = user;
    }

    public void addProduct (Product product) {
        Integer amount = products.putIfAbsent(product, 1);
        if (amount == null) {
            products.put(product, products.get(product) + 1);
        }
    }

    public void removeProduct(Product product) {
        new BoughtProductValidator().validateProductInBasket(products, product);
        products.remove(product);
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
