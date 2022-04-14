package controller;

public class InputValidator {

    public boolean validateEmail(String email) {
        if (isTextEmpty(email)) {
            return false;
        }
        if (email.length() < 5
                || email.lastIndexOf('.') <= email.indexOf('@') + 1
                || email.indexOf('@') < 1
                || email.lastIndexOf('.') == email.length() - 1) {
            return false;
        }
        return true;
    }

    public boolean validateNumber(String number) {
        String tmp = number.trim();
        if (isTextEmpty(tmp)) {
            return false;
        }
        int i = 0;
        if (tmp.charAt(i) == '+' || tmp.charAt(i) == '-') {
            i++;
        }
        int start=i;
        while (i < tmp.length() && Character.isDigit(tmp.charAt(i))) {
            ++i;
        }
        return i!=start && i == number.length();
    }

    private boolean isTextEmpty(String tmp) {
        return tmp == null || tmp.isBlank();
    }

    public boolean validatePassword(String password) {
        if (password.length() < 7) {
            return false;
        }
        return true;
    }
}
