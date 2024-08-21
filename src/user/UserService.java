package user;

import java.util.ArrayList;
import java.util.List;

public interface UserService {
    static void main(String[] args) {
        final UserService service = new UserServiceImpl();

        try {
            User user = service.createUser(new UserParam("bossxomlut", "this is a password", "bossxomlut@gmail.com"));
            System.out.println("create user successfully: " + user);
        } catch (Exception e) {
            System.out.println("create user have errors: " + e.getMessage());
        }
    }

    User createUser(UserParam userParam);
}


class UserServiceImpl implements UserService {
    private final List<UserValidator> validators = new ArrayList(List.of(new AccountValidator(), new PasswordValidator(), new EmailValidator()));

    @Override public User createUser(UserParam userParam) {
        final User user = userParam.build();

        for (UserValidator validator : validators) {
            if (!validator.isValid(user)) {
                throw new IllegalStateException(validator.getInvalidMessage());
            }
        }

        return user;
    }
}