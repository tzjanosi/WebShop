package main;

import controller.*;
import org.mariadb.jdbc.MariaDbDataSource;
import repositories.BasketRepository;
import repositories.DBSource;
import repositories.ProductRepository;
import repositories.UserRepository;
import service.*;

public class Main {
    public static void main(String[] args) {
        DBSource dbSource = new DBSource("/webshop.properties");
        MariaDbDataSource dataSource = dbSource.getDataSource();

        BasketServiceRepository basketRepository = new BasketRepository(dataSource);
        ProductServiceRepository productRepository = new ProductRepository(dataSource);
        UserServiceRepository userRepository = new UserRepository(dataSource);

        BasketControllerService basketService = new BasketService(basketRepository);
        ProductControllerService productService = new ProductService(productRepository);
        UserControllerService userService = new UserService(userRepository);
        WebShopController controller = new WebShopController(basketService, productService, userService, new WebShopControllerValidator());

        userService.registerUser("kisrozal@gmail.com", "password");
        userService.registerUser("Fehervirag@freemail.hu", "pass987word");
        userService.registerUser("szepvirag@citromail.hu", "123password");

        MainMenu mainMenu = new MainMenu(controller, new InputValidator());
        mainMenu.menu();
    }
}
