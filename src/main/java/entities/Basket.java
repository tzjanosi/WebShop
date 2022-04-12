package entities;

import java.util.HashMap;
import java.util.Map;

public class Basket {

    private final Long id;
    private final User user;
    private Map<Product, Integer> products = new HashMap<>();

    public Basket(Long id, User user) {
        this.id = id;
        this.user = user;
    }

    public void addProduct (Product product) {
        Integer amount = products.putIfAbsent(product, 1);
        if (amount == null) {
            products.put(product, products.get(product) + 1);
        }
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
