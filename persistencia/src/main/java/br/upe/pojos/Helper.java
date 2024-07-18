package br.upe.pojos;

import java.lang.reflect.Method;

public class Helper {

    public static boolean isGetter(Method method) {
        return method.getName().startsWith("get") && method.getParameterCount() == 0 && !void.class.equals(method.getReturnType());
    }

    public static String getSetterName(String getterName) {
        return "set" + getterName.substring(3);
    }
}
