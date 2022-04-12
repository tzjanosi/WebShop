package repositories;
import entities.User;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User(
                rs.getLong("id"),
                rs.getString("email"),
                rs.getInt("pass")
//                rs.getString("name")
        );
        return user;
    }
}
