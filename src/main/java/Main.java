import entities.Product;
import org.mariadb.jdbc.MariaDbDataSource;
import repositories.DBSource;
import service.*;

public class Main {
    public static void main(String[] args) {
        DBSource dbSource= new DBSource("/webshop.properties");
        MariaDbDataSource dataSource= dbSource.getDataSource();

        UserService userService=new UserService(dataSource);
        ProductService productService=new ProductService(dataSource);



        //Gyors teszt
        productService.insertProduct(new Product("Vaj",400));
        productService.insertProduct(new Product("WC papir",800));
        productService.insertProduct(new Product("Csoki",300));
        productService.insertProduct(new Product("Kenyér",600));
        productService.insertProduct(new Product("Tej",398));

        System.out.println(productService.findProductByName("Kenyér").get());


        userService.registerUser("kisrozal@gmail.com", "password");
        userService.registerUser("Fehervirag@freemail.hu", "pass987word");
        userService.registerUser("szepvirag@citromail.hu", "123password");

        System.out.println("Login (OK):"+userService.loginUser("Fehervirag@freemail.hu", "pass987word"));
//        System.out.println("Login (NOK):"+userService.loginUser("Fehervirag@freemail.hu", "pass87word"));
//        System.out.println("Login (NOK):"+userService.loginUser("Fehervrag@freemail.hu", "pass987word"));

    }
}
