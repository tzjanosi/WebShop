package repositories;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesHandler {
    private Properties properties;
    private String location;

    public PropertiesHandler(String location) {
        properties = new Properties();
        this.location = location;
    }

    public void readProperties(){
        try(BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(PropertiesHandler.class.getResourceAsStream(location)))) {
            properties.load(bufferedReader);
        }
        catch (IOException ioe){
            throw new IllegalStateException("Can't read properties file!",ioe);
        }
    }

    public Properties getProperties() {
        return properties;
    }
}
