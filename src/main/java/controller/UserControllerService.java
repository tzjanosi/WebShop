package controller;

import entities.User;

import java.util.Optional;

public interface UserControllerService {

    Optional<User> loginUser(String email, String password);

    Optional<User> registerUser(String email, String password);
}
