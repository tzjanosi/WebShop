package controller;

import entities.User;

import java.util.Optional;

public interface UserControllerService {

    Optional<User> loginUser(String email, String password);

    boolean registerUser(String email, String password);
}
