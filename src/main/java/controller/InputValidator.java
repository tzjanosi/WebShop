package controller;

public class InputValidator {

    private boolean validateEmail(String email) {
        if (email.length() < 5
                || email.lastIndexOf('.') <= email.indexOf('@') + 1
                || email.indexOf('@') < 1
                || email.lastIndexOf('.') == email.length() - 1) {
            return false;
        }
        return true;
    }
}
