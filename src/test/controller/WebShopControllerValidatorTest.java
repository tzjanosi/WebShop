package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;
import repositories.BasketRepository;
import repositories.DBSource;
import repositories.ProductRepository;
import repositories.UserRepository;
import service.*;

import static org.junit.jupiter.api.Assertions.*;

class WebShopControllerValidatorTest {

    WebShopController controller;

    @BeforeEach
    void init() {
        DBSource dbSource = new DBSource("/webshop.properties");
        MariaDbDataSource dataSource = dbSource.getDataSource();

        BasketServiceRepository basketRepository = new BasketRepository(dataSource);
        ProductServiceRepository productRepository = new ProductRepository(dataSource);
        UserServiceRepository userRepository = new UserRepository(dataSource);

        BasketControllerService basketService = new BasketService(basketRepository);
        ProductControllerService productService = new ProductService(productRepository);
        UserControllerService userService = new UserService(userRepository);
        WebShopController controller = new WebShopController(basketService, productService, userService, new WebShopControllerValidator());

    }


    @Test
    void testIsUserExists() {

    }

}