package authentication;

import commander.Query;
import commander.QueryToObject;


public class LogoutRequestFromQuery implements QueryToObject<LogoutRequest> {
    @Override public LogoutRequest toObject(Query query) {
        LogoutRequest logoutRequest = new LogoutRequest();

        logoutRequest.setProperties(query.getMap());

        return logoutRequest;
    }
}
