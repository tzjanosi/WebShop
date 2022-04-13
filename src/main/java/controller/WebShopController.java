package controller;

import entities.Basket;
import entities.Product;
import entities.User;
import service.ProductService;
import service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class WebShopController {

    private static final String PATH_OF_PRODUCTS = "src/main/resources/products.txt";

    private static final List<String> YES_NO_ANSWERS = Arrays.asList("y", "yes", "i", "igen", "n", "no", "nem");

    private BasketControllerService basketService;

    private ProductControllerService productService;

    private UserControllerService userService;

    private Basket actualOrder;

    private List<Product> products;

    private InputValidator validator;

    public WebShopController(BasketControllerService basketService, ProductControllerService productService, UserControllerService userService) {
        this.basketService = basketService;
        this.productService = productService;
        this.userService = userService;
        fillProductsIntoDB();
        products = productService.createListOfProducts();
        //teszthez
        List<Product> tesztproducts = new ArrayList<>();
        tesztproducts.add(new Product(1L, "Vaj", 300));
        tesztproducts.add(new Product(2L, "Tej", 200));
        tesztproducts.add(new Product(1L, "Kenyér", 500));
        products = tesztproducts;
        validator = new InputValidator();
    }

    public void menu() {
        int numberOfOptions;
        int option;
        do {
//            System.out.print("Felhasználó: ");
//            printActualOrder();
            numberOfOptions = printUserMenu();
            option = getOptionNumber(numberOfOptions);
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

    private void printActualUser() {
        if (actualOrder.getUser() == null) {
            System.out.println("NINCS BEJELENTKEZETT FELHASzNÁLÓ!");
        } else {
            System.out.println(actualOrder.getUser().getEmail());
        }
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

    private void saveOrder() {
        printActualOrder();
        System.out.println("Biztos a vásárlásban?");
        boolean trustedOrder = askSaveOrder();
        if (trustedOrder) {
            basketService.saveOrder(actualOrder);
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

    private void growsOrderedProductAmount() {
        printActualOrder();
        List<String> options = actualOrder.getProducts().keySet().stream()
                .map(product -> product.getName() + ", " + product.getPrice() + "Ft")
                .toList();
        int numberOfProducts = printListOfOptions(options);
        System.out.print("Melyik termékből szeretne többet vásárolni? ");
        int indexOfProduct = getOptionNumber(numberOfProducts) - 1;
        Product product = products.get(indexOfProduct);
        System.out.print("Mennyivel növeli a vásárolt mennyiséget? ");
        Scanner sc = new Scanner(System.in);
        int amount = sc.nextInt();
        actualOrder.addProduct(product, amount);
        actualOrder.removeProduct(product);

    }

    private void removeProductFromOrder() {
        printActualOrder();
        List<String> options = actualOrder.getProducts().keySet().stream()
                .map(product -> product.getName() + ", " + product.getPrice() + "Ft")
                .toList();
        int numberOfProducts = printListOfOptions(options);
        System.out.print("Melyik termébket szeretné törölni? ");
        int indexOfProduct = getOptionNumber(numberOfProducts) - 1;
        Product product = products.get(indexOfProduct);
        actualOrder.removeProduct(product);
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
                .map(product -> product.getName() + ", " + product.getPrice() + "Ft")
                .toList();
        int numberOfProducts = printListOfOptions(options);
        System.out.print("Melyik terméből szeretne vásárolni? ");
        int indexOfProduct = getOptionNumber(numberOfProducts) - 1;
        Product product = products.get(indexOfProduct);
        System.out.print("Várásolni kívánt mennyiség: ");
        Scanner sc = new Scanner(System.in);
        int amount = sc.nextInt();
        actualOrder.addProduct(product, amount);
    }

    public void loginOrRegistration() {
        List<String> options = Arrays.asList("Belépés", "Regisztráció");
        printListOfOptions(options);
        int option = getOptionNumber(options.size());
        switch (option) {
            case 1:
                resultOfLogin(login());
                return;
            case 2:
                resultOfRegistration(registration());
                return;
        }
    }

    private void resultOfRegistration(Optional<User> user) {
        if (user.isEmpty()) {
            System.out.println("Az e-mail cím már regisztrálva van!");
        } else {
            actualOrder = new Basket(user.get());
            System.out.println("Üdvözlöm a WebShop-ban!");
        }
    }

    private void resultOfLogin(Optional<User> user) {
        if (user.isEmpty()) {
            System.out.println("Hibás e-mail cím vagy jelszó!");
        } else {
            //basket id?
            actualOrder = new Basket(user.get());
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
        printActualUser();
        System.out.println("Vásárolt termékek:");
        if (actualOrder.getProducts().isEmpty()) {
            System.out.println("Nincs még termék a rendelésben!\n");
        } else {
            actualOrder.getProducts()
                    .forEach((product, amount) -> System.out.printf("%s: mennyiség: %d, ár: %dFt\n", product.getName(), amount, amount * product.getPrice()));
            System.out.println();
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
