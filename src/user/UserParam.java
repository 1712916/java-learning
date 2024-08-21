package user;

public record UserParam(String account, String password, String email) implements User.Builder {

    @Override public User build() {
        return new User.UserBuilder()
                .setAccount(account)
                .setPassword(password)
                .setEmail(email)
                .build();
    }
}
