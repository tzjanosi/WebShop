package repositories;

import entities.User;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    private JdbcTemplate jdbcTemplate;

    public UserRepository(DataSource dataSource) {
        jdbcTemplate =new JdbcTemplate(dataSource);
    }

    public Optional<User> saveUser(User userToSave) {
        jdbcTemplate.update("INSERT INTO users (email,pass,name) VALUES(?,?,?);",userToSave.getEmail(), userToSave.getPassword(),"");
//        jdbcTemplate.update("INSERT INTO users (email,pass,name) VALUES(?,?,?);",userToSave.getEmail(), userToSave.password(),userToSave.getName());
        return Optional.empty();
    }

    public Optional<User> findUserByEmail(String email) {
        List<User> result=jdbcTemplate.query("SELECT * FROM users WHERE email = ? ORDER BY id DESC",new UserMapper(), email);
        if(result.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(result.get(0));
    }
}
