package repositories;

import org.flywaydb.core.Flyway;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;
import java.util.Properties;

public class DBSource {
    private Properties properties;
    private MariaDbDataSource dataSource;
    private static boolean initFlyWay=true;

    public DBSource(String locationOfPropertiesFile) {
        PropertiesHandler propertiesHandler=new PropertiesHandler(locationOfPropertiesFile);
        propertiesHandler.readProperties();
        properties=propertiesHandler.getProperties();
        initDB();
    }

    private void initDB(){
        createDataSource(properties.getProperty("schema"),properties.getProperty("userName"),properties.getProperty("password"));
        if(DBSource.initFlyWay){
            initFlyway(properties.getProperty("migrationDirectory"));
        }
    }

    private void createDataSource(String schema, String username, String password){
        String url="jdbc:mariadb://localhost:3306/"+schema+"?useUnicode=true";
        try{
            dataSource = new MariaDbDataSource();
            dataSource.setUrl(url);
            dataSource.setUser(username);
            dataSource.setPassword(password);
        }
        catch (SQLException sqle) {
            throw new IllegalStateException("Can not create data source", sqle);
        }
    }

    private void initFlyway(String migrationDirectory){
        DBSource.initFlyWay=false;
        Flyway flyway = Flyway.configure().locations(migrationDirectory).dataSource(dataSource).load();
        flyway.clean();
        flyway.migrate();
    }

    public MariaDbDataSource getDataSource() {
        return dataSource;
    }
}
