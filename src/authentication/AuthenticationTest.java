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
        showAllUsers();
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
                                LoginRequest loginRequest = new LoginRequest();
                                //param format: -a bossxomlut -pw password -dv device123
                                List<String> params = Arrays.asList(param.split(" "));
                                Map<String, String> properties = new HashMap<>();

                                for (int i = 0; i < params.size(); i++) {
                                    String data = params.get(i);
                                    if (data.startsWith("-")) {
                                        properties.put(data, params.get(i + 1));
                                    }
                                }


                                String account = properties.get("-a");
                                String password = properties.get("-pw");
                                String deviceId = properties.get("-dv");


                                System.out.println("account: " + account);
                                System.out.println("password: " + password);
                                System.out.println("deviceId: " + deviceId);

                                loginRequest.setProperties(Map.of("account", account, "password", password,
                                        "deviceId", deviceId));
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
                                RegisterRequest registerRequest = new RegisterRequest();
                                //param format: -a bossxomlut -pw password
                                String account = "";
                                String password = "";

                                account = param.substring(param.indexOf("-a") + 2, param.indexOf("-pw") - 1);
                                password = param.substring(param.indexOf("-pw") + 3);

                                registerRequest.setProperties(Map.of("account", account, "password", password));
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