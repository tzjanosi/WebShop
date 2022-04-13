package repositories;

import entities.Basket;
import org.springframework.jdbc.core.JdbcTemplate;
import service.BasketServiceRepository;

import javax.sql.DataSource;

public class BasketRepository implements BasketServiceRepository {
    private JdbcTemplate jdbcTemplate;

    public BasketRepository(DataSource dataSource) {
        jdbcTemplate =new JdbcTemplate(dataSource);
    }

    @Override
    public boolean saveOrder(Basket basket) {
        return false;
    }
}
