package com.zcm.library.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Create by zcm on 2018/8/3 下午3:36
 */
public class ReflectUtil {
    /**
     * 反射调用对象方法
     * @param instance 对象实例
     * @param methodName 方法名
     * @param parameterTypes 方法参数类型
     * @param params 方法实际参数
     * @return
     */
    public static Object invokeMethod(Object instance, String methodName,Class<?>[] parameterTypes,Object[] params) {
        Class<?> clazz = instance.getClass();
        Method method;
        try {
            method = clazz.getDeclaredMethod(methodName,parameterTypes);
            return method.invoke(instance,params);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
