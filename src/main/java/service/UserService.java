package service;

import controller.UserControllerService;
import entities.User;
import repositories.UserRepository;
import validators.UserValidator;

import javax.sql.DataSource;
import java.util.Optional;

public class UserService implements UserControllerService {

    private UserRepository userRepository;
    private UserValidator validator;

    public UserService(DataSource dataSource) {
        this.userRepository = new UserRepository(dataSource);
        this.validator = new UserValidator(userRepository);
    }

    @Override
    public Optional<User> registerUser(String email, String password) {
        email = email.strip();
        password = password.strip();
        if (validator.checkIfUserExists(email)) {
            return Optional.empty();
        }
        validator.validateRegistration(email, password);
        return userRepository.saveUser(new User(email, password));
    }

    @Override
    public Optional<User> loginUser(String email, String password) {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if (optionalUser.isEmpty()) {
            return optionalUser;
        }
        if (optionalUser.get().getPassword() != password.hashCode()) {
            return Optional.empty();
        }
        return optionalUser;
    }
}
