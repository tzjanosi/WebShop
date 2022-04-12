package repositories;

import entities.Product;
import org.mariadb.jdbc.MariaDbDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

public class ProductRepository {
    private JdbcTemplate jdbcTemplate;

    public ProductRepository() {
        DBSource dbSource= new DBSource("/webshop.properties");
        MariaDbDataSource dataSource= dbSource.getDataSource();
        jdbcTemplate =new JdbcTemplate(dataSource);
    }

    public void insertProduct(Product product) {
    }

    public Optional<Product> findProductByName(String name) {
        return Optional.empty();
    }
}
