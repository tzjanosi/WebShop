package controller;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InputValidatorTest {

    InputValidator validator = new InputValidator();

    @Test
    void testValidateNumber() {
        assertAll(
                () -> assertEquals(true, validator.validateNumber("11")),
                () -> assertEquals(true, validator.validateNumber("-1"))
        );
    }

    @Test
    void testValidateNumberWrongInput() {
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,
                () -> validator.validateNumber("a"));

        assertAll(
                () -> assertEquals("Nem számot adott meg!", iae.getMessage()),
                () -> assertEquals(NumberFormatException.class, iae.getCause().getClass())
        );
    }

    @Test
    void testValidateNumberInRange() {
        assertEquals(true, validator.validateNumberInRange(12, 13));
    }

    @Test
    void testValidateNumberOutOfRange() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> validator.validateNumberInRange(-1, 3)),
                () -> assertThrows(IllegalArgumentException.class, () -> validator.validateNumberInRange(0, 3)),
                () -> assertEquals("Nincs ilyen számú lehetőség: 4",
                        assertThrows(IllegalArgumentException.class, () -> validator.validateNumberInRange(4, 3)).getMessage())
        );
    }

    @Test
    void testValidateEmail() {
        assertEquals(true, validator.validateEmail("a@a.a"));
    }

    @Test
    void testValidateEmailWithNull() {
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,
                () -> validator.validateEmail(null));

        assertEquals("Az e-mail null!", iae.getMessage());
    }

    @Test
    void testValidateEmailWithEmptyString() {
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,
                () -> validator.validateEmail("       "));

        assertEquals("Az e-mail üres!", iae.getMessage());
    }

    @Test
    void testValidateEmailWithFormalProblem() {
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,
                () -> validator.validateEmail("a.a@a"));

        assertEquals("Érvénytelen e-mail cím!: a.a@a", iae.getMessage());
    }

    @Test
    void testValidatePassword() {
        assertEquals(true, validator.validatePassword("1234567"));
    }

    @Test
    void testValidateTooShortPassword() {
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,
                () -> validator.validatePassword("123456"));

        assertEquals("A jelszó túl rövid! (Min: 7 karakter)!", iae.getMessage());
    }

    @Test
    void testValidatePasswordWithNull() {
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,
                () -> validator.validatePassword(null));

        assertEquals("A jelszó null!", iae.getMessage());
    }

    @Test
    void testValidatePasswordWithEmpty() {
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,
                () -> validator.validatePassword("     "));

        assertEquals("A jelszó üres!", iae.getMessage());
    }


    @Test
    void testValidateIsThereMissingProduct() {
        assertEquals(true, validator.isThereMissingProduct(List.of("termék")));
    }

    @Test
    void testValidateIsThereMissingProductFailed() {
        IllegalStateException ise = assertThrows(IllegalStateException.class,
                () -> validator.isThereMissingProduct(new ArrayList<>()));

        assertEquals("Már minden termékből rendelt!", ise.getMessage());
    }

    @Test
    void testValidateIsOrderedProductsEmpty() {
        assertEquals(false, validator.isOrderedProductsEmpty(List.of("termék")));
    }

    @Test
    void testValidateIsOrderedProductsEmptyFailed() {
        IllegalStateException ise = assertThrows(IllegalStateException.class,
                () -> validator.isOrderedProductsEmpty(new ArrayList<>()));

        assertEquals("A megrendelt termékek listája üres!", ise.getMessage());
    }

}