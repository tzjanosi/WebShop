package controller;

import entities.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MainMenu {

    private List<String> options = new ArrayList<>();

    private WebShopController webShopController;

    private InputValidator validator;

    public static final List<String> NO_ANSWERS = Arrays.asList("n", "no", "nem");
    public static final List<String> YES_ANSWERS = Arrays.asList("y", "yes", "i", "igen");

    public MainMenu(WebShopController webShopController, InputValidator validator) {
        this.webShopController = webShopController;
        this.validator = validator;
        initOptions();
    }

    public void menu() {
        int option;
        List<String> actualOptions;
        do {
            System.out.println("-".repeat(80));
            printActualOrder();
            System.out.println("-".repeat(60));
            System.out.println("Válasszon a lehetőségek közül:");
            if (webShopController.isUserPresent()) {
                actualOptions = options;
            } else {
                actualOptions = Arrays.asList(options.get(0), options.get(options.size() - 1));
            }
            option = getOption(actualOptions);
            System.out.println("-".repeat(80));
            processOption(option);
        } while (option != actualOptions.size());
        System.out.println("Viszont látásra!");
    }

    private int printNumberedList(List<String> options, String label) {
        System.out.println(label);
        for (int i = 1; i <= options.size(); i++) {
            System.out.printf("%2d %s%n", i, options.get(i - 1));
        }
        return options.size();
    }

    private void initOptions() {
        options.add("Felhasználó regisztráció/bejelentkezés");
        options.add("Termékek belehelyezése a vásárló kosarába");
        options.add("Termék kivétele vásárló kosarából");
        options.add("Termék mennyiség növelése");
        options.add("Rendelés leadása");
        options.add("Termékek listázása/betöltése fájlból");
        options.add("Kilépés a WebShop-ból");
    }


    private void processOption(int optionNumber) {
        switch (optionNumber) {
            case 1:
                try {
                    loginOrRegistration();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                return;
            case 2:
                try {
                    addProductToOrder();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                return;
            case 3:
                try {
                    removeProductFromOrder();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                return;
            case 4:
                try {
                    growsOrderedProductAmount();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                return;

            case 5:
                try {
                    saveOrder();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                return;
            case 6:
                try {
                    refreshProducts();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                return;
            default:
        }
    }

    private void printActualOrder() {
        printActualUser();
        if (webShopController.isUserPresent()) {
            if (webShopController.isOrderedProductsEmpty()) {
                System.out.println("Vásárolt termékek:");
                System.out.println("Nincs még termék a rendelésben!\n");
            } else {
                List<String> orderedProducts = webShopController.getFormattedListOfOrderedProducts("%s, %ddb, %dFt");
                System.out.println("Vásárolt termékek:");
                printListItems(orderedProducts);
                System.out.println();
            }
        }
    }

    private boolean printActualUser() {
        System.out.print("Felhasználó: ");
        boolean userLoggedIn = webShopController.isUserPresent();
        if (userLoggedIn) {
            System.out.println(webShopController.getUserEmail());
        } else {
            System.out.println("NINCS BEJELENTKEZETT FELHASZNÁLÓ!");
        }
        return userLoggedIn;
    }

    private void loginOrRegistration() {
        List<String> subOptions = Arrays.asList("Belépés", "Regisztráció");
        int option = getOption(subOptions);
        System.out.println("-".repeat(40));
        switch (option) {
            case 1:
                System.out.println("Belépés:");
                login();
                return;
            case 2:
                System.out.println("Regisztráció:");
                registration();
                return;
            default:
        }
    }

    private void registration() {
        String email = getValidEmail();
        String password = getValidPassword();
        boolean result = webShopController.registration(email, password);
        if (result) {
            System.out.println("Sikeres regisztráció, kérem jelentkezzen be!");
        } else {
            System.out.println("Sikertelen regisztráció! A megadott e-mail cím már regisztrálva van!");
        }
    }

    private void login() {
        String email = getValidEmail();
        String password = getValidPassword();
        boolean result = webShopController.login(email, password);
        if (result) {
            System.out.println("Üdvözlöm a WebShop-ban!");
        }
    }

    private String getValidEmail() {
        Scanner sc = new Scanner(System.in);
        String email = "";
        boolean valid = false;
        do {
            try {
                System.out.print("Kérem az e-mail címet: ");
                email = sc.nextLine();
                valid = validator.validateEmail(email);
            } catch (IllegalArgumentException iae) {
                System.out.println(iae.getMessage());
            }
        } while (!valid);
        return email;
    }

    private String getValidPassword() {
        Scanner sc = new Scanner(System.in);
        String password = "";
        boolean valid = false;
        do {
            try {
                System.out.print("Kérem a jelszót: ");
                password = sc.nextLine();
                valid = validator.validatePassword(password);
            } catch (IllegalArgumentException iae) {
                System.out.println(iae.getMessage());
            }
        } while (!valid);
        return password;
    }

    private void addProductToOrder() {
        printActualOrder();
        System.out.println("Rendelhető termékek: ");
        List<String> products = new ArrayList<>(webShopController.getProductNames());
        products.removeAll(webShopController.getOrderedProductNames());
        validator.validateOrderedProductsNotFull(products);
        String productName = products.get(getOption(products) - 1);
        Product product = webShopController.getProductByName(productName);
        webShopController.addProductToOrder(product);
        System.out.println(product.getName() + " termék hozzáadva a rendeléshez!");
    }

    private void removeProductFromOrder() {
        List<String> orderedProducts = webShopController.getOrderedProductNames();
        validator.validateOrderedProductsEmpty(orderedProducts);
        System.out.println("Melyik terméket szeretné törölni?");
        String productName = orderedProducts.get(getOption(orderedProducts) - 1);
        Product product = webShopController.getProductByName(productName);
        webShopController.removeProductFromOrder(product);
        System.out.println(product.getName() + " termék törölve a rendelésből!");
    }

    private void growsOrderedProductAmount() {
        List<String> orderedProducts = webShopController.getOrderedProductNames();
        validator.validateOrderedProductsEmpty(orderedProducts);
        System.out.println("Melyik termékből szeretne többet rendelni?");
        String productName = orderedProducts.get(getOption(orderedProducts) - 1);
        Product product = webShopController.getProductByName(productName);
        int amount = getNumber("Mennyivel növeli a vásárolt mennyiséget? ");
        webShopController.addProductToOrder(product, amount);
    }


    public void refreshProducts() {
        webShopController.loadProductsIntoDB();
        List<String> newProducts = webShopController.getFormattedListOfProducts("%s, %dFt");
        printNumberedList(newProducts, "A termékek frissített listája: ");
    }

    public void saveOrder() {
        printActualOrder();
        System.out.print("Biztos a vásárlásban? ");
        boolean signedOrder = askSaveOrder();
        if (signedOrder) {
            try {
                webShopController.saveOrder();
                System.out.println("A rendelés leadása megtörtént! Köszönjük, hogy nálunk vásárolt!");
                return;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("A rendelés véglegesítése visszavonva! Folytathatja a rendelést!");
    }

    private boolean askSaveOrder() {
        Scanner sc = new Scanner(System.in);
        String answer = sc.nextLine().toLowerCase();
        if (YES_ANSWERS.contains(answer)) {
            return true;
        } else if (NO_ANSWERS.contains(answer)) {
            return false;
        }
        throw new IllegalArgumentException("Hibás adat a megrendelés véglegesítésekor: " + answer);
    }


    private int getOption(List<String> options) {
        int numberOfOptions = printListItems(options);
        int selected = 0;
        boolean valid = false;
        do {
            try {
                selected = getNumber("Kérem válasszon (1-" + numberOfOptions + "): ");
                valid = validator.validateNumberInRange(selected, numberOfOptions);
            } catch (IllegalArgumentException iae) {
                System.out.println(iae.getMessage());
            }
        } while (!valid);
        return selected;
    }

    private int printListItems(List<String> items) {
        for (int i = 0; i < items.size(); i++) {
            System.out.printf("%2d. %s%n", i + 1, items.get(i));
        }
        return items.size();
    }

    private int getNumber(String label) {
        Scanner sc = new Scanner(System.in);
        String text;
        do {
            System.out.print(label);
            text = sc.nextLine();
        } while (!validator.validateNumber(text));
        return Integer.parseInt(text);
    }
}
