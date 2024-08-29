package dependency_injection;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

interface Injector {
    static Injector get() {
        return InjectorFactory.instance();
    }

    <T> void register(Class<T> type, Function<Object, T> factoryFunction);

    <T> T getInstance(Class<T> type, Object arg);

    void  resetAll();

    <T> void reset(Class<T> type);

    public static void main(String[] args) {
        Injector injector = Injector.get();
        injector.register(FooClass.class, arg -> new Hello());
        FooClass fooClass = injector.getInstance(FooClass.class, null);
        fooClass.foo();
    }
}


class InjectorFactory implements Injector {
    private InjectorFactory() {}

    static private Injector Instance = new InjectorFactory();

    static public Injector instance() {
        return Instance;
    }

    Map<Class<?>,Function<Object, Object>> map = new HashMap<Class<?>, Function<Object, Object>>();

    @Override
    public <T> void register(Class<T> type, Function<Object, T> factoryFunction) {
        map.put(type, (Function<Object, Object>) factoryFunction);
    }

    @Override
    public <T> T getInstance(Class<T> type, Object arg) {
       try {
           return (T) map.get(type).apply(arg);
       } catch (NullPointerException e) {
           throw new DidNotRegisterTypeException(type.getName() + "is not registered");
       }
    }

    @Override
    public void resetAll() {
        map.clear();
    }

    @Override
    public <T> void reset(Class<T> type) {
        map.remove(type);
    }
}

interface FooClass {
    void foo();
}

class Hello implements FooClass {
    @Override
    public void foo() {
        System.out.println("Hello");
    }
}


class DidNotRegisterTypeException extends RuntimeException {
    public DidNotRegisterTypeException(String message) {
        super(message);
    }
}