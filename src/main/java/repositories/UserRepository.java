package repositories;

import entities.User;

import org.springframework.jdbc.core.JdbcTemplate;
import service.UserServiceRepository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class UserRepository implements UserServiceRepository {
    private JdbcTemplate jdbcTemplate;

    public UserRepository(DataSource dataSource) {
        jdbcTemplate =new JdbcTemplate(dataSource);
    }

    public boolean saveUser(User userToSave) {
        jdbcTemplate.update("INSERT INTO users (email,pass,name) VALUES(?,?,?);",userToSave.getEmail(), userToSave.getPassword(),"");
//        jdbcTemplate.update("INSERT INTO users (email,pass,name) VALUES(?,?,?);",userToSave.getEmail(), userToSave.password(),userToSave.getName());
        return true;
    }

    public Optional<User> findUserByEmail(String email) {
        List<User> result=jdbcTemplate.query("SELECT * FROM users WHERE email = ? ORDER BY id DESC",new UserMapper(), email);
        if(result.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(result.get(0));
    }
}
