package repositories;

import entities.User;

import org.mariadb.jdbc.MariaDbDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

public class UserRepository {
    private JdbcTemplate jdbcTemplate;

    public UserRepository() {
        DBSource dbSource= new DBSource("/webshop.properties");
        MariaDbDataSource dataSource= dbSource.getDataSource();
        jdbcTemplate =new JdbcTemplate(dataSource);
    }

    public void insertUser(User user) {
    }

    public Optional<User> findUserByEmail(String email) {
        return Optional.empty();
    }
}
