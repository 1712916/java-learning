package authentication;

import java.util.Map;

public interface Authentication {
    public LoginResponse login( LoginRequest request);

    public LogoutResponse logout(   LogoutRequest request);

    public RegisterResponse register(   RegisterRequest request);

    public ChangePasswordResponse changePassword(   ChangePasswordRequest request);
}


enum AuthenStatus {
    SUCCESS,
    FAIL
}

abstract class AuthenResponse {
    AuthenStatus status;

    String message;

    public AuthenStatus getStatus() {
        return status;
    }

    public void setStatus(AuthenStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

class LoginResponse extends AuthenResponse {}

class RegisterResponse extends AuthenResponse {}

abstract class ChangePasswordResponse extends AuthenResponse {}

class LogoutResponse extends AuthenResponse {}

abstract class AuthenRequest {
    private Map<String, String> properties;

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}

class LoginRequest extends AuthenRequest {}

class RegisterRequest extends AuthenRequest {}

abstract class ChangePasswordRequest extends AuthenRequest {}

abstract class LogoutRequest extends AuthenRequest {}
