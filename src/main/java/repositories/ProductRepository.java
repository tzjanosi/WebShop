package repositories;

import entities.Product;

import entities.User;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class ProductRepository {
    private JdbcTemplate jdbcTemplate;

    public ProductRepository(DataSource dataSource) {
        jdbcTemplate =new JdbcTemplate(dataSource);
    }

    public void saveProduct(Product productToSave) {
        jdbcTemplate.update("INSERT INTO product (name,price,amount) VALUES(?,?,?);",productToSave.getName(), productToSave.getPrice(),0);
//        jdbcTemplate.update("INSERT INTO product (name,price,amount) VALUES(?,?,?);",productToSave.getName(), productToSave.getPrice(),productToSave.getAmount());

    }

    public Optional<Product> findProductByName(String name) {
        List<Product> result=jdbcTemplate.query("SELECT * FROM product WHERE name = ? ORDER BY id DESC",new ProductMapper(), name);
        if(result.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(result.get(0));
    }
}
