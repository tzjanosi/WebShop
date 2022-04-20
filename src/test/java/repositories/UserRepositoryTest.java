package repositories;

import entities.User;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;
import service.UserServiceRepository;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
    UserRepository userRepository;

    @BeforeEach
    void init() {
        DBSource dbSource = new DBSource("/webshop.properties");
        MariaDbDataSource dataSource = dbSource.getDataSource();
        userRepository = new UserRepository(dataSource);
    }

    @Test
    void insertAndGetProductByNameTest() {
        userRepository.saveUser(new User("kisrozal@gmail.com", "password"));
        User user=userRepository.findUserByEmail("kisrozal@gmail.com").get();
        assertAll(
                () -> assertEquals("kisrozal@gmail.com",user.getEmail()),
                () -> assertEquals("password".hashCode(),user.getPassword())
        );


    }
}