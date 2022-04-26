package repositories;

import entities.User;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
    UserRepository userRepository;
    String migrationDirectory="db/migrations";

    @BeforeEach
    void init() {
        JdbcDataSource dataSource = new JdbcDataSource();

        dataSource.setUrl("jdbc:h2:~/test");
        dataSource.setUser("sa");
        dataSource.setPassword("sa");

        Flyway flyway = Flyway.configure().locations(migrationDirectory).dataSource(dataSource).load();

        flyway.clean();
        flyway.migrate();

        userRepository = new UserRepository(dataSource);
    }

    @Test
    @DisplayName("User insert into the DB and findUser test")
    void insertAndGetProductByNameTest() {
        userRepository.saveUser(new User("kisrozal@gmail.com", "password"));
        User user=userRepository.findUserByEmail("kisrozal@gmail.com").get();
        assertAll(
                () -> assertEquals("kisrozal@gmail.com",user.getEmail()),
                () -> assertEquals("password".hashCode(),user.getPassword())
        );
    }
}