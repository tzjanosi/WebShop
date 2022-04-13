import org.mariadb.jdbc.MariaDbDataSource;
import repositories.DBSource;
import service.*;

public class Main {
    public static void main(String[] args) {
        DBSource dbSource= new DBSource("/webshop.properties");
        MariaDbDataSource dataSource= dbSource.getDataSource();

        UserService userService=new UserService(dataSource);
        ProductService productService=new ProductService(dataSource);


    }
}
