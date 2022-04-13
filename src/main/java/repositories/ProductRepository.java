package repositories;

import entities.Product;

import entities.User;
import org.springframework.jdbc.core.JdbcTemplate;
import service.ProductServiceRepository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class ProductRepository implements ProductServiceRepository {
    private JdbcTemplate jdbcTemplate;

    public ProductRepository(DataSource dataSource) {
        jdbcTemplate =new JdbcTemplate(dataSource);
    }

    public void saveProduct(Product productToSave) {
        jdbcTemplate.update("INSERT INTO product (name,price,amount) VALUES(?,?,?);",productToSave.getName(), productToSave.getPrice(),0);
//        jdbcTemplate.update("INSERT INTO product (name,price,amount) VALUES(?,?,?);",productToSave.getName(), productToSave.getPrice(),productToSave.getAmount());

    }

    @Override
    //csak hogy tudjak tesztelni
    public List<Product> getAllProducts() {
        return jdbcTemplate.query("select * from product",
                (rs, rowNum) -> new Product(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("price")));
    }

    @Override
    public Optional<Product> findProductByName(String name) {
        List<Product> result=jdbcTemplate.query("SELECT * FROM product WHERE name = ? ORDER BY id DESC",new ProductMapper(), name);
        if(result.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(result.get(0));
    }

    @Override
    public void insertProduct(Product vaj) {

    }
}
