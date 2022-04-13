package controller;

import entities.Basket;
import entities.User;
import service.BasketService;
import service.ProductService;
import service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class WebShopController {

    private BasketService basketService;

    private ProductService productService;

    private UserService userService;

    private User actualUser;

    private Basket actualOrder;

    public WebShopController(BasketService basketService, ProductService productService, UserService userService) {
        this.basketService = basketService;
        this.productService = productService;
        this.userService = userService;
    }

    //teszt
    public WebShopController() {
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
            System.out.printf("%2d %s\n", i, options.get(i-1));
        }
        return options.size();
    }

    private void processOption(int optionNumber) {
        //kiszervezni metódusokba
        switch (optionNumber) {
            case 1:
                //még a regisztrációt kezelni
                Scanner sc = new Scanner(System.in);
                System.out.print("Email: ");
                String emailAddress = sc.nextLine();
                System.out.print("Password: ");
                String password = sc.nextLine();
                userService.loginUser(emailAddress, password);
//                Optional<User> user =  userService.loginUser(emailAddress, password);
                // nem jó a boolean, nincs userem
                Optional<User> user = Optional.of(new User(emailAddress, password));
                if (user.isEmpty()) {
                    System.out.println("Hibás e-mail cím vagy jelszó!");
                } else {
                    System.out.println("Üdvözlöm a WebShop-ban!");
                    actualUser = user.get();
                }
                return;
            case 2:
                return;
            case 3:
                return;
            case 4:
                return;
            case 5:
                return;
            case 6:
                return;
        }
    }
}
