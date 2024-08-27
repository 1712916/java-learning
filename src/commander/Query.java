package commander;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Query {
    private final Map<String, String> map;

    private Query(Map<String, String> map) {
        this.map = map;
    }

    public static Query fromString(String input, String separate) {
        //account=user,password=password,email=email

        Map<String, String> map = new HashMap<String, String>();

        String[] pairs = input.split(separate);

        for (String pair : pairs) {
            String[] keyValue = pair.split("=");

            if (keyValue.length != 2) {
                continue;
            }

            map.put(keyValue[0], keyValue[1]);
        }

        return new Query(map);
    }

    public Optional<String> get(String key) {
        return Optional.ofNullable(map.get(key));
    }

    public int size() {
        return map.size();
    }

    public Map<String, String> getMap() {
        return map;
    }
}
