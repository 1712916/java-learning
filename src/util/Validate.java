package util;

public  interface Validate<T> {

    boolean isValid(T value);

    String getInvalidMessage();
}
