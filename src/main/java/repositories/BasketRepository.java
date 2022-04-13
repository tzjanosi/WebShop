package repositories;

import entities.Basket;
import entities.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import service.BasketServiceRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Map;

public class BasketRepository implements BasketServiceRepository {
    private JdbcTemplate jdbcTemplate;

    public BasketRepository(DataSource dataSource) {
        jdbcTemplate =new JdbcTemplate(dataSource);
    }

    @Override
    public boolean saveOrder(Basket basketToSave) {
        try {
            long buyingID = insertBasketIntoBuying(basketToSave);
            for (Map.Entry<Product, Integer> entry : basketToSave.getProducts().entrySet()) {
                jdbcTemplate.update("INSERT INTO bought_product (buying_id,product_id,amount) VALUES(?,?,?);", buyingID, entry.getKey().getId(), entry.getValue());
            }
        }
        catch(SQLException e){
            return false;
        }
        return true;
    }

    private long insertBasketIntoBuying(Basket basketToSave) throws SQLException{
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> preparedStatementForinsert(con,basketToSave.getUser().getId()), keyHolder);
        return keyHolder.getKey().longValue();
    }
    private PreparedStatement preparedStatementForinsert(Connection con, long userID) throws SQLException {
        String sql = "INSERT INTO buying (user_id,date_and_time_of_buying) VALUES(?,NOW());";
        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setLong(1, userID);
        return ps;
    }
}
