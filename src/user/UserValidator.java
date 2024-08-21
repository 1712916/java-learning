package user;

import util.Validate;

interface UserValidator extends Validate<User> {}

class EmailValidator implements UserValidator {
    @Override public boolean isValid(User user) {
        return user.getEmail().filter(email -> email.contains("@")).isPresent();
    }

    @Override public String getInvalidMessage() {
        return "Email is invalid";
    }
}

class AccountValidator implements UserValidator {
    @Override public boolean isValid(User user) {
        return user.getAccount().isPresent();
    }

    @Override public String getInvalidMessage() {
        return "Account is invalid";
    }
}

class PasswordValidator implements UserValidator {
    @Override public boolean isValid(User user) {
        return user.getPassword().isPresent();
    }

    @Override public String getInvalidMessage() {
        return "Password is invalid";
    }
}
