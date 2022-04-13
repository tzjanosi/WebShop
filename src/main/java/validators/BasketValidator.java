package validators;

import entities.Product;

import java.util.Map;

public class BasketValidator {

    public void validateProductInBasket(Map<Product, Integer> products, Product product) {
        if (!products.containsKey(product)) {
            throw new IllegalArgumentException("Basket does not contains " + product.getName() + "!");
        }
    }
}
