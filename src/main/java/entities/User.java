package entities;

public class User {

    private Long id;
    private String email;
    private int password;

    /**
    * Constructor for user registration.
    **/
    public User(String email, String password) {
        this.email = email;
        this.password = password.hashCode();
    }

    /**
    * Constructor for creating user by database.
     **/
    public User(Long id, String email, int password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }
}
