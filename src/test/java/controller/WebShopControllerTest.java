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

class WebShopControllerTest {

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
        controller = new WebShopController(basketService, productService, userService, new WebShopControllerValidator());


        userService.registerUser("kisrozal@gmail.com", "password");
        userService.registerUser("Fehervirag@freemail.hu", "pass987word");
        userService.registerUser("szepvirag@citromail.hu", "123password");
    }


    @Test
    void testLogin() {
        assertEquals(true, controller.login("kisrozal@gmail.com", "password"));
    }

    @Test
    void testLoginWithFormalError() {
        assertEquals("Érvénytelen e-mail cím!: ksi@k", assertThrows(IllegalArgumentException.class,
                () -> controller.login("ksi@k", "password")).getMessage());
        assertEquals("A jelszó túl rövid!", assertThrows(IllegalArgumentException.class,
                () -> controller.login("ksi@k.com", "pas")).getMessage());
    }

    @Test
    void testLoginWithUnregisteredUser() {
        assertEquals(false, controller.login("kisrozal@gmail.com", "PASSWORD"));
        assertEquals(false, controller.login("KISSROZAL@GMAIL.COM", "password"));
        assertEquals(false, controller.login("KISSROZAL@GMAIL.COM", "PASSWORD"));
    }

    @Test
    void testRegistration() {
        assertEquals(true, controller.registration("újfelhasználó@valami.com", "1234567"));
    }

    @Test
    void testRegistrationWithAlreadyRegisteredEmailAddress() {
        assertEquals(false, controller.registration("kisrozal@gmail.com", "PASSWORD"));
    }

    @Test
    void testRegistrationWithFormalError() {
        assertEquals("Érvénytelen e-mail cím!: ksi@k", assertThrows(IllegalArgumentException.class,
                () -> controller.registration("ksi@k", "password")).getMessage());
        assertEquals("A jelszó túl rövid!", assertThrows(IllegalArgumentException.class,
                () -> controller.registration("ksi@k.com", "pas")).getMessage());
    }

    @Test
    void testUserLoggedIn() {
        controller.login("kisrozal@gmail.com", "password");

        assertEquals(true, controller.isUserPresent());
    }

    @Test
    void testUserNotLoggedIn() {
        assertEquals(false, controller.isUserPresent());
    }

    @Test
    void testGetUserEmail() {
        controller.login("kisrozal@gmail.com", "password");
        assertEquals("kisrozal@gmail.com", controller.getUserEmail());
    }

    @Test
    void testGetUserEmailWithoutLogin() {
        assertEquals("The e-mail is null!", assertThrows(IllegalStateException.class,
                () -> controller.getUserEmail()).getMessage());
    }

    @Test
    void testIsOrderedProductsEmpty() {
        controller.login("kisrozal@gmail.com", "password");
        assertEquals(true, controller.isOrderedProductsEmpty());
    }

}