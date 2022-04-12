package service;

import entities.User;
import repositories.UserRepository;
import validators.UserValidator;

public class UserService {

    private UserRepository userRepository;
    private UserValidator validator;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.validator = new UserValidator(userRepository);
    }

    public void registerUser(String email, String password) {
        email = email.strip();
        password = password.strip();
        validator.checkIfUserExists(email);
        validator.validateRegistration(email, password);
        userRepository.insertUser(new User(email, password));
    }

    public void loginUser(String email, String password) {

    }
}
