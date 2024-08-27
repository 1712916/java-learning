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

class LoginRequest extends AuthenRequest {
    public String getAccount() {
        return getProperties().get("account");
    }

    public String getPassword() {
        return getProperties().get("password");
    }

    public String getDeviceId() {
        return getProperties().get("deviceId");
    }

    public void setAccount(String account) {
        getProperties().put("account", account);
    }

    public void setPassword(String password) {
        getProperties().put("password", password);
    }

    public void setDeviceId(String deviceId) {
        getProperties().put("deviceId", deviceId);
    }
}

class RegisterRequest extends AuthenRequest {}

abstract class ChangePasswordRequest extends AuthenRequest {}

  class LogoutRequest extends AuthenRequest {
    String getAccount() {
        return getProperties().get("account");
    }

    String getDeviceId() {
        return getProperties().get("deviceId");
    }
  }
