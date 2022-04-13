package controller;

import entities.Basket;
import entities.Product;
import entities.User;
import service.BasketService;
import service.ProductService;
import service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class WebShopController {

    private static final String PATH_OF_PRODUCTS = "src/main/resources/products.txt";

    private BasketService basketService;

    private ProductService productService;

    private UserService userService;

    private User actualUser;

    private Basket actualOrder;

    private List<Product> products;

    public WebShopController(BasketService basketService, ProductService productService, UserService userService) {
        this.basketService = basketService;
        this.productService = productService;
        this.userService = userService;
        fillProductsIntoDB();
        products = productService.createListOfProducts();
    }

    public void menu() {
        int numberOfOptions;
        int option;
        do {
            numberOfOptions = printUserMenu();
            option = getOptionNumber(numberOfOptions);
            System.out.println(option);
            processOption(option);
        } while (option != numberOfOptions);
    }

    //majd enum-ra átalakítani
    private int printUserMenu() {
        List<String> options = new ArrayList<>();
        options.add("Felhasználó regisztráció/bejelentkezés");
        options.add("Termékek belehelyezése a vásárló kosarába");
        options.add("Termék kivétele vásárló kosarából");
        options.add("Termék mennyiség növelése");
        options.add("Rendelés leadása");
        options.add("Termékek listázása/betöltése fájlból");
        options.add("Kilépés a WebShop-ból");
        System.out.println("Menü: ");
        return printListOfOptions(options);
    }

    private int getOptionNumber(int optionNumbers) {
        System.out.print("Kérem a választott menüpont számát: ");
        Scanner sc = new Scanner(System.in);
        //int option = getNumberFrom console();
        return sc.nextInt();
    }

    private int printListOfOptions(List<String> options) {
        for (int i = 1; i <= options.size(); i++) {
            System.out.printf("%2d %s\n", i, options.get(i - 1));
        }
        return options.size();
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
                return;
            case 4:
                return;
            case 5:
                return;
            case 6:
                refreshProducts();
                return;
        }

    }

    private void refreshProducts() {
        fillProductsIntoDB();
        products = productService.createListOfProducts();
        System.out.println("A termékek frissített listája: ");
        List<String> options = products.stream()
                .map(product -> product.getName() + "egységár" + product.getPrice())
                .toList();
        products.forEach(System.out::println);
    }

    //itt lehetne szűrni hogy ami már bent van azt ne írja ki újra
    private void addProductToOrder() {
        printActualOrder();
        System.out.println("A vásárolható termékek listája: ");
        List<String> options = products.stream()
                .map(product -> product.getName() + "egységár" + product.getPrice())
                .toList();
        int option = printListOfOptions(options);
        System.out.print("Várásolni kívánt mennyiség: ");
        Scanner sc = new Scanner(System.in);
        int amount = sc.nextInt();
        actualOrder.addProduct(products.get(option), amount);
    }

    public void loginOrRegistration() {
        List<String> options = Arrays.asList("Belépés", "Regisztráció");
        printListOfOptions(options);
        int option = getOptionNumber(options.size());
        Optional<User> user = Optional.empty();
        switch (option) {
            case 1:
                user = login();
                resultOfLogin(user);
                return;
            case 2:
                user = registration();
                resultOfRegistration(user);
                return;
        }
    }

    private void resultOfRegistration(Optional<User> user) {
        if (user.isEmpty()) {
            System.out.println("Az e-mail cím már regisztrálva van!");
        } else {
            actualUser = user.get();
            // Basket id?
            actualOrder = new Basket(1L, actualUser);
            System.out.println("Üdvözlöm a WebShop-ban!");
        }
    }

    private void resultOfLogin(Optional<User> user) {
        if (user.isEmpty()) {
            System.out.println("Hibás e-mail cím vagy jelszó!");
        } else {
            actualUser = user.get();
            //basket id?
            actualOrder = new Basket(1L, actualUser);
            System.out.println("Üdvözlöm a WebShop-ban!");
        }
    }

    private Optional<User> login() {
        Scanner sc = new Scanner(System.in);
        String emailAddress = getEmailAddress();
        String password = getPassword();
        userService.loginUser(emailAddress, password);
        return userService.loginUser(emailAddress, password);

    }

    private Optional<User> registration() {
        String emailAddress = getEmailAddress();
        String password = getPassword();
        userService.loginUser(emailAddress, password);
        return userService.registerUser(emailAddress, password);
    }

    //esetleg, ha van már mentve
    private String getEmailAddress() {
        boolean valid = false;
        String email = null;
        while (!valid) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Email: ");
            email = sc.nextLine();
            valid = isEmailValid(email);
            if (!valid) {
                System.out.println("Érvénytelen e-mail cím!");
            }
        }
        return email;
    }

    private boolean isEmailValid(String email) {
        return true;
    }

    private String getPassword() {
        boolean valid = false;
        String email = null;
        while (!valid) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Password: ");
            email = sc.nextLine();
            valid = isPasswordValid(email);
            if (!valid) {
                System.out.println("Érvénytelen password!");
            }
        }
        return email;
    }

    private boolean isPasswordValid(String password) {
        return true;
    }

    private void printActualOrder() {
        System.out.printf("Vásárló: %s", actualUser.getEmail());
        System.out.println("Vásárolt termékek:");
        if (actualOrder.getProducts().isEmpty()) {
            System.out.println("Nincs még termék a rendelésben!");
        } else {
            actualOrder.getProducts().forEach((product, amount) -> System.out.printf("%s: %dFt",product, amount));
        }
    }

    private void fillProductsIntoDB() {
        try (BufferedReader br = Files.newBufferedReader(Path.of(PATH_OF_PRODUCTS))) {
            List<Product> productsToSave = new ArrayList<>();
            String lines;
            while ((lines = br.readLine()) != null) {
                String[] parts = lines.split(";");
                productsToSave.add(new Product(parts[0], Integer.parseInt(parts[1])));
            }
            productService.insertMultipleProducts(productsToSave);
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot load products!", ioe);
        }
    }
}
