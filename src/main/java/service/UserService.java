package service;

import entities.User;
import repositories.UserRepository;
import validators.UserValidator;

import java.util.Optional;

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
        if (validator.checkIfUserExists(email)) {
            throw new IllegalArgumentException("Email: '" + email + "' had been registered!");
        }
        validator.validateRegistration(email, password);
        userRepository.insertUser(new User(email, password));
    }

    public boolean loginUser(String email, String password) {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("Email: '" + email + "' is not registered!");
        }
        if (optionalUser.get().getPassword() != password.hashCode()) {
            throw new IllegalArgumentException("Password didn't match for user: " + email + "!");
        }
        return true;
    }
}
