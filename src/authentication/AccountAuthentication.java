package authentication;

import user.User;

import java.util.Optional;

public class AccountAuthentication implements  Authentication {

    AccountValidator accountValidator = new AccountValidator();

    @Override public LoginResponse login(LoginRequest request) {
        if (!accountValidator.isValid(request)) {
            LoginResponse response = new LoginResponse();
            response.setStatus(AuthenStatus.FAIL);
            response.setMessage(accountValidator.getInvalidMessage());
            return response;
        }

        String account = request.getAccount();

        Optional<User> existUser = AuthenticationTest.findByAccount(account);

        if (existUser.isEmpty()) {
            LoginResponse response = new LoginResponse();
            response.setStatus(AuthenStatus.FAIL);
            response.setMessage("This account is not already exist");
            return response;
        }


        String password = request.getPassword();
        if (existUser.filter(user -> user.getPassword().orElse("_").equals(password)).isEmpty()) {
            LoginResponse response = new LoginResponse();
            response.setStatus(AuthenStatus.FAIL);
            response.setMessage("Password is incorrect");
            return response;
        }

        String deviceId = request.getDeviceId();

        if (deviceId == null || deviceId.isEmpty()) {
            LoginResponse response = new LoginResponse();
            response.setStatus(AuthenStatus.FAIL);
            response.setMessage("Device id is required");
            return response;
        }

        if (AuthenticationTest.isLogin(account)) {
            if (AuthenticationTest.compareDeviceIds(account, deviceId)){
                LoginResponse response = new LoginResponse();
                response.setStatus(AuthenStatus.SUCCESS);
                response.setMessage("Login success");
                return response;
            }

            LoginResponse response = new LoginResponse();
            response.setStatus(AuthenStatus.FAIL);
            response.setMessage("This account is already login\n Please logout first in the previous device");
            return response;
        }


        AuthenticationTest.login(account, deviceId);

        LoginResponse response = new LoginResponse();
        response.setStatus(AuthenStatus.SUCCESS);
        response.setMessage("Login success");
        return response;
    }

    @Override public LogoutResponse logout(LogoutRequest request) {

        String account = request.getAccount();
        String deviceId = request.getDeviceId();

        if (!AuthenticationTest.isLogin(account)) {
            LogoutResponse response = new LogoutResponse();
            response.setStatus(AuthenStatus.FAIL);
            response.setMessage("This account is not login");
            return response;
        }

        if (!AuthenticationTest.compareDeviceIds(account, deviceId)) {
            LogoutResponse response = new LogoutResponse();
            response.setStatus(AuthenStatus.FAIL);
            response.setMessage("This account is not login in this device");
            return response;
        }

        AuthenticationTest.logout(account, deviceId);

        LogoutResponse response = new LogoutResponse();
        response.setStatus(AuthenStatus.SUCCESS);
        response.setMessage("Logout success");
        return response;
    }

    @Override public RegisterResponse register(RegisterRequest request) {
        if (!accountValidator.isValid(request)) {
            RegisterResponse response = new RegisterResponse();
            response.setStatus(AuthenStatus.FAIL);
            response.setMessage(accountValidator.getInvalidMessage());
            return response;
        }

        String account = request.getProperties().get("account");

        Optional<User> existUser = AuthenticationTest.findByAccount(account);

        if (existUser.isPresent()) {
            RegisterResponse response = new RegisterResponse();
            response.setStatus(AuthenStatus.FAIL);
            response.setMessage("This account is already exist");
            return response;
        }

        //create account

        User user = new User.UserBuilder()
                .setAccount(account)
                .setPassword(request.getProperties().get("password"))
                .build();

        AuthenticationTest.addUser(user);

        RegisterResponse response = new RegisterResponse();
        response.setStatus(AuthenStatus.SUCCESS);
        response.setMessage("Register success");
        return response;
    }

    @Override public ChangePasswordResponse changePassword(ChangePasswordRequest request) {
        return null;
    }
}

//validate
class AccountValidator implements AuthenRequestValidator {
    @Override public boolean isValid(AuthenRequest request) {
        return Optional.ofNullable(request.getProperties().get("account")).isPresent();
    }

    @Override public String getInvalidMessage() {
        return "Account is invalid";
    }
}