package main;

import controller.BasketControllerService;
import controller.ProductControllerService;
import controller.UserControllerService;
import controller.WebShopController;
import entities.Product;
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

        BasketServiceRepository basketRepository = new BasketRepository();
        ProductServiceRepository productRepository = new ProductRepository(dataSource);
        UserServiceRepository userRepository = new UserRepository(dataSource);

        BasketControllerService basketService = new BasketService(basketRepository);
        ProductControllerService productService = new ProductService(productRepository);
        UserControllerService userService = new UserService(userRepository);

        //Gyors teszt
        productRepository.insertProduct(new Product("Vaj", 400));
        productRepository.insertProduct(new Product("WC papir", 800));
        productRepository.insertProduct(new Product("Csoki", 300));
        productRepository.insertProduct(new Product("Kenyér", 600));
        productRepository.insertProduct(new Product("Tej", 398));

//        System.out.println(productRepository.findProductByName("Kenyér").get());


        userService.registerUser("kisrozal@gmail.com", "password");
        userService.registerUser("Fehervirag@freemail.hu", "pass987word");
        userService.registerUser("szepvirag@citromail.hu", "123password");

        System.out.println("Login (OK):" + userService.loginUser("Fehervirag@freemail.hu", "pass987word"));
//        System.out.println("Login (NOK):"+userService.loginUser("Fehervirag@freemail.hu", "pass87word"));
//        System.out.println("Login (NOK):"+userService.loginUser("Fehervrag@freemail.hu", "pass987word"));

        WebShopController webShopController = new WebShopController((BasketService) basketService, (ProductService) productService, (UserService) userService);
        webShopController.menu();

    }
}
