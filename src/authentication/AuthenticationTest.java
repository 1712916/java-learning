package authentication;

import commander.*;
import user.User;

import java.util.*;
import java.util.function.Supplier;

public class AuthenticationTest {
    static private List<User> users          = new ArrayList<>();

    static void addUser(User user) {
        users.add(user);
    }

    static Optional<User> findByAccount(String account) {
        //showAllUsers();
        return users.stream()
                .filter(user -> user.getAccount().isPresent() && user.getAccount().get().equals(account))
                .findFirst();
    }

    //show all users
    static void showAllUsers() {
        users.forEach(user -> {
            System.out.println("Account: " + user.getAccount().orElse(""));
            System.out.println("Password: " + user.getPassword().orElse(""));
        });
    }

    /*
    * Storage login status and the device id
    * One user can log in with one device
    * If user log in with another device, the previous device will be logout
    * */
    static  private Map<String, String> loginWithDeviceId = new HashMap<>();

    static void login(String account, String deviceId) {
        loginWithDeviceId.put(account, deviceId);
    }

    static boolean isLogin(String account) {
        return loginWithDeviceId.containsKey(account);
    }

    static Optional<String> deviceId(String account) {
        return Optional.ofNullable(loginWithDeviceId.get(account));
    }

    static boolean compareDeviceIds(String account, String deviceId) {
        return deviceId.equals(loginWithDeviceId.get(account));
    }

    public static void logout(String account, String deviceId) {
        if (isLogin(account) && compareDeviceIds(account, deviceId)) {
            loginWithDeviceId.remove(account);
            return;
        }

        throw new RuntimeException("Logout fail");
    }

    public static void main(String[] args) {
        Authentication authentication = new AccountAuthentication();

        Commander commander = CommanderBuilder
                .create()
                .withCommand(Command.EXIT.command, ExitProcessor::new)
                .withCommand(AuthenCommander.LOGIN.command, new Supplier<Processor>() {
                    @Override public Processor get() {
                        return  new Processor() {
                            @Override public void execute(String param) {
                                LoginRequest loginRequest = new LoginRequestFromQuery().toObject(Query.fromString(param, ","));

                                LoginResponse loginResponse = authentication.login(loginRequest);
                                switch (loginResponse.getStatus()) {
                                    case SUCCESS:
                                        System.out.println("Login success");
                                        break;
                                    case FAIL:
                                        System.out.println("Login fail: " + loginResponse.getMessage());
                                        break;
                                }
                            }
                        };
                    }
                })
                .withCommand(AuthenCommander.REGISTER.command, new Supplier<Processor>() {
                    @Override public Processor get() {
                        return  new Processor() {
                            @Override public void execute(String param) {
                                RegisterRequest registerRequest = new RegisterRequestFromQuery().toObject(Query.fromString(param, ","));

                                RegisterResponse registerResponse = authentication.register(registerRequest);
                                switch (registerResponse.getStatus()) {
                                    case SUCCESS:
                                        System.out.println("Register success");
                                        break;
                                    case FAIL:
                                        System.out.println("Register fail: " + registerResponse.getMessage());
                                        break;
                                }
                            }
                        };
                    }
                })
                .withCommand(AuthenCommander.LOGOUT.command, new Supplier<Processor>() {
                    @Override public Processor get() {
                        return  new Processor() {
                            @Override public void execute(String param) {
                                LogoutRequest logoutRequest = new LogoutRequestFromQuery().toObject(Query.fromString(param, ","));

                                LogoutResponse logoutResponse = authentication.logout(logoutRequest);
                                switch (logoutResponse.getStatus()) {
                                    case SUCCESS:
                                        System.out.println("Logout success");
                                        break;
                                    case FAIL:
                                        System.out.println("Logout fail: " + logoutResponse.getMessage());
                                        break;
                                }
                            }
                        };
                    }
                })
                .build();

        commander.readLoop();
    }
}


enum AuthenCommander {
    LOGIN("login"),
    LOGOUT("logout"),
    REGISTER("register"),
    CHANGE_PASSWORD("change-password");

    String command;

    AuthenCommander(String command) {
        this.command = command;
    }
}