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

    public static final List<String> YES_NO_ANSWERS = Arrays.asList("y", "yes", "i", "igen", "n", "no", "nem");

    public MainMenu(WebShopController webShopController, InputValidator validator) {
        this.webShopController = webShopController;
        this.validator = validator;
        initOptions();
    }

    public void menu() {
        int numberOfOptions;
        int option;
        do {
            System.out.println("-".repeat(80));
            printActualOrder();
            numberOfOptions = printMainMenu();
            System.out.println("-".repeat(80));
            option = getOptionNumber(numberOfOptions, "Kérem a választott menüpont számát: ", "Nincs ilyen menüpont!");
            processOption(option);
        } while (option != numberOfOptions);
    }

    private int printMainMenu() {
        String label = "Menü";
        int numberOfOptions;
        if (webShopController.isUserPresent()) {
            numberOfOptions = printNumberedList(options, label);
        } else {
            numberOfOptions = printNumberedList(Arrays.asList(options.get(0), options.get(options.size() - 1)), label);
        }
        return numberOfOptions;
    }

    private int printNumberedList(List<String> options, String label) {
        System.out.println(label);
        for (int i = 1; i <= options.size(); i++) {
            System.out.printf("%2d %s\n", i, options.get(i - 1));
        }
        return options.size();
    }

    private int getOptionNumber(int optionNumbers, String label, String errortext) {
        boolean valid = false;
        int number = 0;
        while (!valid) {
            number = getNumber(label, errortext);
            if (number > 0 && number <= optionNumbers) {
                valid = true;
            } else {
                System.out.println(errortext);
            }
        }
        return number;
    }


    private int getNumber(String label, String errorText) {
        System.out.print(label);
        Scanner sc = new Scanner(System.in);
        String text;
        while (!validator.validateNumber(text = sc.nextLine())) {
            System.out.print(errorText + "\n" + label);
        }
        return Integer.parseInt(text.strip());
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
        //kiszervezni metódusokba
        switch (optionNumber) {
            case 1:
                loginOrRegistration();
                return;
            case 2:
                addProductToOrder();
                return;
            case 3:
                removeProductFromOrder();
                return;
            case 4:
                growsOrderedProductAmount();
                return;
            case 5:
                saveOrder();
                return;
            case 6:
                refreshProducts();
                return;
        }
    }


    //ToDo
    private void printActualOrder() {
        printActualUser();
        if (webShopController.isUserPresent()) {
            if (webShopController.isExistsOrderedProduct()) {
                List<String> orderedProducts = webShopController.getListOfFormattedOrderedProducts("%s, %ddb, %dFt");
                printNumberedList(orderedProducts, "Vásárolt termékek:");
            } else {
                System.out.println("Vásárolt termékek:");
                System.out.println("Nincs még termék a rendelésben!\n");
            }
        }
    }

    private boolean printActualUser() {
        System.out.print("Felhasználó: ");
        if (webShopController.isUserPresent()) {
            System.out.println(webShopController.getUserEmail());
        } else {
            System.out.println("NINCS BEJELENTKEZETT FELHASZNÁLÓ!");
        }
        return webShopController.isUserPresent();
    }

    private void loginOrRegistration() {
        List<String> options = Arrays.asList("Belépés", "Regisztráció");
        printNumberedList(options, "Válaszzon a lehetőségek közül:");
        int option = getOptionNumber(options.size(), "Kérem a választott menüpont számát: ", "Nincs ilyen menüpont!");
        switch (option) {
            case 1:
                login();
                return;
            case 2:
                registration();
                return;
        }
    }

    private void registration() {
        String email;
        String password;
        boolean result;
        email = getEmailAddress();
        password = getPassword();
        result = webShopController.registration(email, password);
        if (result) {
            System.out.println("Regisztráció sikeres, kérem jelentkezzen be!");
        } else {
            System.out.println("A regisztráció sikertelen!");
        }
    }

    private void login() {
        String email;
        String password;
        boolean result;
        email = getEmailAddress();
        password = getPassword();
        result = webShopController.login(email, password);
        if (result) {
            System.out.println("Üdvözlöm a WebShop-ban!");
        } else {
            System.out.println("Hibás e-mail cím vagy jelszó!");
        }
    }

    private String getEmailAddress() {
        boolean valid = false;
        String email = null;
        while (!valid) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Email: ");
            email = sc.nextLine();
            valid = validator.validateEmail(email);
            if (!valid) {
                System.out.println("Érvénytelen e-mail cím!");
            }
        }
        return email;
    }

    private String getPassword() {
        boolean valid = false;
        String password = null;
        while (!valid) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Password: ");
            password = sc.nextLine();
            valid = validator.validatePassword(password);
            if (!valid) {
                System.out.println("Érvénytelen jelszó!");
            }
        }
        return password;
    }

    private void addProductToOrder() {
        printActualOrder();
        Product product = getProductFromFormattedProductList();
        int amount = getNumber("Várásolni kívánt mennyiség: ", "Hibás adat!");
        webShopController.addProductToOrder(product, amount);
        System.out.println(product.getName() + " termék " + amount + " darabszámmal hozzáadva a rendeléshez!");
    }

    private Product getProductFromFormattedProductList() {
        List<String> formattedProductList = webShopController.getListOfFormattedProducts("%s, %dFt");
        int numberOfProducts = printNumberedList(formattedProductList, "Melyik terméből szeretne vásárolni? ");
        int indexOfProduct = getOptionNumber(numberOfProducts, "Termék sorszáma: ", "Nincs ilyen számú termék! ") - 1;
        return getProduct(formattedProductList, indexOfProduct);
    }

    private void removeProductFromOrder() {
        printActualOrder();
        Product product = getProductFromFormattedOrderedProductList("Melyik terméket szeretné törölni?");
        webShopController.removeProductFromOrder(product);
        System.out.println(product.getName() + " termék törölve a rendelésből!");
    }

    private void growsOrderedProductAmount() {
        printActualOrder();
        Product product = getProductFromFormattedOrderedProductList("Melyik terméket szeretné törölni?");
        System.out.print("Mennyivel növeli a vásárolt mennyiséget? ");
        Scanner sc = new Scanner(System.in);
        int amount = sc.nextInt();
        webShopController.addProductToOrder(product, amount);
    }

    private Product getProductFromFormattedOrderedProductList(String label) {
        List<String> formattedProductList = webShopController.getListOfFormattedOrderedProducts("%s, %ddb, %dFt");
        int numberOfProducts = printNumberedList(formattedProductList, "Melyik terméből szeretne vásárolni? ");
        int indexOfProduct = getOptionNumber(numberOfProducts, "Termék sorszáma: ", "Nincs ilyen számú termék! ") - 1;
        Product product = getProduct(formattedProductList, indexOfProduct);
        return product;
    }

    private Product getProduct(List<String> formattedProductList, int indexOfProduct) {
        int endIndex = formattedProductList.get(indexOfProduct).indexOf(',');
        String productName = formattedProductList.get(indexOfProduct).substring(0, endIndex);
        return webShopController.getProductByName(productName);
    }


    public void refreshProducts() {
        webShopController.loadProductsIntoDB();
        List<String> newProducts = webShopController.getListOfFormattedProducts("%s, %dFt");
        printNumberedList(newProducts, "A termékek frissített listája: ");
    }

    public void saveOrder() {
        printActualOrder();
        System.out.println("Biztos a vásárlásban?");
        boolean trustedOrder = askSaveOrder();
        if (trustedOrder) {
            webShopController.saveOrder();
        }
    }

    private boolean askSaveOrder() {
        Scanner sc = new Scanner(System.in);
        String answer = sc.nextLine();
        if (YES_NO_ANSWERS.contains(answer.toLowerCase())) {
            return true;
        } else {
            return false;
        }
    }
}
