package service;

import entities.User;

import java.util.Optional;

public interface UserServiceRepository {
    Optional<User> findUserByEmail(String email);

    Optional<User> saveUser(User user);
}
