package user;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class User {
    private final Map<String, Object> properties;

    private User(Map<String, Object> properties) {
        this.properties = properties;
    }

    public Optional<String> getAccount() {
        return getProperty("user", String.class);
    }

    public Optional<String> getPassword() {
       return getProperty("password", String.class);
    }

    public Optional<String> getEmail() {
        return getProperty("email", String.class);
    }

    @SuppressWarnings("unchecked")
    private <T> Optional<T> getProperty(String key, Class<T> type) {
        return Optional.ofNullable((T) properties.get(key));
    }

    public static class UserBuilder {
        private final Map<String, Object> properties = new HashMap<>();

        public User build() {
            return new User(properties);
        }

        public UserBuilder setAccount(String account) {
            properties.put("user", account);
            return this;
        }

        public UserBuilder setPassword(String password) {
            properties.put("password", password);
            return this;
        }

        public UserBuilder setEmail(String email) {
            properties.put("email", email);
            return this;
        }
    }

    public void main(String[] args) {
        User user = new UserBuilder()
                .setAccount("user")
                .setPassword("password")
                .setEmail("email")
                .build();

        System.out.println("account: " + user.properties.get("user"));
    }

    interface Builder {
                public User build();
    }
}


