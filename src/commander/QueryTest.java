package commander;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class QueryTest {
    @Test void getQueryWithPerfectInput() {
        Query query = Query.fromString("account=bossxomlut,password=123123qweqwe,email=e@gmail.com", ",");

        Assertions.assertEquals(query.get("account"), Optional.of("bossxomlut"));
        Assertions.assertEquals(query.get("password"), Optional.of("123123qweqwe"));
        Assertions.assertEquals(query.get("email"), Optional.of("e@gmail.com"));
    }

    @Test void getQueryWithMissingValue() {
        Query query = Query.fromString("account=bossxomlut,password=123123qweqwe,email=", ",");

        Assertions.assertEquals(query.get("account"), Optional.of("bossxomlut"));
        Assertions.assertEquals(query.get("password"), Optional.of("123123qweqwe"));
        Assertions.assertEquals(query.get("email"), Optional.empty());
    }

    @Test void getQueryWithMissingValue2() {
        Query query = Query.fromString("account=bossxomlut,password=123123qweqwe,email", ",");

        Assertions.assertEquals(query.get("account"), Optional.of("bossxomlut"));
        Assertions.assertEquals(query.get("password"), Optional.of("123123qweqwe"));
        Assertions.assertEquals(query.get("email"), Optional.empty());
    }

    @Test void getQueryWithMissingValue3() {
        Query query = Query.fromString("account=bossxomlut,password=123123qweqwe", ",");

        Assertions.assertEquals(query.get("account"), Optional.of("bossxomlut"));
        Assertions.assertEquals(query.get("password"), Optional.of("123123qweqwe"));
    }

    @Test void getQueryWithEmptyValue() {
        Query query = Query.fromString("", ",");

        Assertions.assertEquals(query.size(), 0);
    }

    @Test void getQueryWithInvalidSeparator() {
        Query query = Query.fromString("account=bossxomlut,password=123123qweqwe", "&");

        Assertions.assertEquals(query.size(), 0);
    }
}
