package validators;

import service.UserServiceRepository;

public class UserValidator {

    private UserServiceRepository userServiceRepository;

    public UserValidator(UserServiceRepository userServiceRepository) {
        this.userServiceRepository = userServiceRepository;
    }

    public void validateRegistration(String email, String password) {
        validateEmail(email);
        validatePassword(password);
    }

    private void validateEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null!");
        }
        if (email.length() < 5) {
            throw new IllegalArgumentException("Email: '" + email + "' is too short.");
        }
        if (email.indexOf('@') < 1) {
            throw new IllegalArgumentException(
                    "Invalid email: '" + email + "'. It must contain @. It must be at least in the second character of the string!");
        }
        if (email.lastIndexOf('.') <= email.indexOf('@') + 1) {
            throw new IllegalArgumentException(
                    "Invalid email: '" + email + "'. It must contain a dot. The last dot must be after a substring following @!");
        }
    }

    private void validatePassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null!");
        }
        if (password.length() < 7) {
            throw new IllegalArgumentException("Entered password is too short. It must be at least 8 characters!");
        }
    }

    public boolean checkIfUserExists(String email) {
        return userServiceRepository.findUserByEmail(email).isPresent();
    }
}
