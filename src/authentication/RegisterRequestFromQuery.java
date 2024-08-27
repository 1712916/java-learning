package authentication;

import commander.Query;
import commander.QueryToObject;

public class RegisterRequestFromQuery implements QueryToObject<RegisterRequest> {
    @Override public RegisterRequest toObject(Query query) {
        RegisterRequest registerRequest = new RegisterRequest();

        registerRequest.setProperties(query.getMap());

        return registerRequest;
    }
}
