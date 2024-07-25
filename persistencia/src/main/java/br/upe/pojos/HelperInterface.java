package br.upe.pojos;

import java.lang.reflect.Method;

public interface HelperInterface {

    static boolean isGetter(Method method) {
        return method.getName().startsWith("get") && method.getParameterCount() == 0 && !void.class.equals(method.getReturnType());
    }

    static String getSetterName(String getterName) {
        return "set" + getterName.substring(3);
    }

    static <T> void checkout(T source, T destination){
        Method[] methods = source.getClass().getMethods();
        for (Method getter : methods) {
            try{
                if (!HelperInterface.isGetter(getter)) continue;

                Object value = getter.invoke(source);
                if (value == null) continue;

                for (Method setter : methods) {
                    if (setter.getName().equals(HelperInterface.getSetterName(getter.getName()))) {
                        setter.invoke(destination, value);
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Error while checkouting: " + source.getClass().getName() );
                e.printStackTrace();
            }
        }
    }
}
