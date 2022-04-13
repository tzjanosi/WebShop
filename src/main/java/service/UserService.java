package service;

import controller.UserControllerService;
import entities.User;
import validators.UserValidator;

import java.util.Optional;

public class UserService implements UserControllerService {

    private UserServiceRepository userServiceRepository;
    private UserValidator validator;

    public UserService(UserServiceRepository userServiceRepository) {
        this.userServiceRepository = userServiceRepository;
        this.validator = new UserValidator(userServiceRepository);
    }

    @Override
    public boolean registerUser(String email, String password) {
        email = email.strip();
        password = password.strip();
        if (validator.checkIfUserExists(email)) {
            return false;
        }
        validator.validateRegistration(email, password);
        return userServiceRepository.saveUser(new User(email, password));
    }

    @Override
    public Optional<User> loginUser(String email, String password) {
        Optional<User> optionalUser = userServiceRepository.findUserByEmail(email);
        if (optionalUser.isEmpty()) {
            return optionalUser;
        }
        if (optionalUser.get().getPassword() != password.hashCode()) {
            return Optional.empty();
        }
        return optionalUser;
    }
}
