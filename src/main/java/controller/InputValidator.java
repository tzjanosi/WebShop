package controller;

import java.util.List;

public class InputValidator {


    public boolean validateNumber(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Nem számot adott meg!", nfe);
        }
    }

    public boolean validateNumberInRange(int number, int max) {
        if (number > 0 && number <= max) {
            return true;
        }
        throw new IllegalArgumentException("Nincs ilyen számú lehetőség: " + number);
    }

    public boolean validateEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Az e-mail null!");
        }
        if (email.isBlank()) {
            throw new IllegalArgumentException("Az e-mail üres!");
        }
        return isEmailValid(email);
    }

    public boolean validatePassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("A jelszó null!");
        }
        if (password.isBlank()) {
            throw new IllegalArgumentException("A jelszó üres!");
        }
        return isPasswordValid(password);
    }

    private boolean isEmailValid(String email) {
        if (email.length() < 5
                || email.lastIndexOf('.') <= email.indexOf('@') + 1
                || email.indexOf('@') < 1
                || email.lastIndexOf('.') == email.length() - 1) {
            throw new IllegalArgumentException("Érvénytelen e-mail cím!: " + email);
        }
        return true;
    }

    private boolean isPasswordValid(String password) {
        if (password.length() < 7) {
            throw new IllegalArgumentException("A jelszó túl rövid! (Min: 7 karakter!");
        }
        return true;
    }

    public boolean validateOrderedProductsNotFull(List<String> products) {
        if (products.isEmpty()) {
            throw new IllegalStateException("Már minden termékből rendelt!");
        }
        return true;
    }

    public boolean validateOrderedProductsEmpty(List<String> orderedProducts) {
        if (orderedProducts.isEmpty()) {
            throw new IllegalStateException("A megrendelt termékek listája üres!");
        }
        return false;
    }
}
