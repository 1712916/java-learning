package authentication;

import commander.Query;
import commander.QueryToObject;


public class LoginRequestFromQuery implements QueryToObject<LoginRequest> {
    @Override public LoginRequest toObject(Query query) {
        LoginRequest registerRequest = new LoginRequest();

        registerRequest.setProperties(query.getMap());

        return registerRequest;
    }
}
