package controller;

import entities.Basket;
import entities.Product;
import entities.User;

import java.util.List;
import java.util.Optional;

public class WebShopControllerValidator {

    public static final String SQL_ERROR_MESSAGE = "Adatbázis hiba! A kért művelet nem került rögzítésre!";

    public static final String SUPPORT_ERROR_MESSAGE = "Hiba a program működésében, vegye fel a kapcsolatot a szolgáltatóval! Tel.:123456789";

    public static final String FILE_ERROR_MESSAGE = "Hiba az állomány betöltése közben!";

    public boolean isUserExists(Optional<User> user) {
        if (user.isEmpty()) {
            throw new IllegalArgumentException("Hibás e-mail cím vagy jelszó!");
        }
        return true;
    }

    public boolean orderValidator(Basket order) {
        if (order == null) {
            throw new IllegalStateException("Nincs bejelentkezve!");
        }
        if (order.getUser() == null) {
            throw new IllegalStateException(SUPPORT_ERROR_MESSAGE);
        }
        if (order.getProducts().isEmpty()) {
            throw new IllegalStateException("A kosár üres!");
        }
        return true;
    }

    public boolean validateEmail(String email) {
        if (isStringNull(email)) {
            throw new IllegalArgumentException("Az e-mail null!");
        }
        if (isStringEmpty(email)) {
            throw new IllegalArgumentException("Az e-mail üres!");
        }
        return isEmailValid(email);
    }

    public boolean validatePassword(String password) {
        if (isStringNull(password)) {
            throw new IllegalArgumentException("A jelszó null!");
        }
        if (isStringEmpty(password)) {
            throw new IllegalArgumentException("A jelszó üres!");
        }
        return isPasswordValid(password);
    }

    private boolean isStringNull(String text) {
        return text == null;
    }

    private boolean isStringEmpty(String text) {
        return text.isBlank();
    }

    private boolean isEmailValid(String email) {
        if (email.length() < 5
                || email.lastIndexOf('.') <= email.indexOf('@') + 1
                || email.indexOf('@') < 1
                || email.lastIndexOf('.') == email.length() - 1) {
            throw new IllegalArgumentException("Érvénytelen e-mail-cím!: " + email);
        }
        return true;
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 7;
    }

    public boolean validateFormattedListOfOrderedProducts(List<String> result) {
        if (result == null) {
            throw new IllegalArgumentException("A megrendelt termékek listája null.");
        }
        if (result.isEmpty()) {
            throw new IllegalArgumentException("A megrendelt termékek listája üres.");
        }
        return true;
    }

    public boolean validateProduct(List<Product> products, Product product) {
        if(products.contains(product)) {
            return true;
        }
        throw new IllegalArgumentException("A keresett termék nincs a listában.");

    }
}
