package com.dtds.mobileplatform.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*****************************************
 * @description:反射相关工具类
 * @author:lixiaohui
 * @date: 2018/6/26
 * @company:深圳动态网络科技有限公司
 *****************************************/
public class ReflectionUtil {
    private static final String TAG = "ReflectionUtil";

    /**
     * 获得字段的字节映射(反射对象)
     *
     * @param className
     * @param fieldName
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchFieldException
     */
    public static Field getField(String className, String fieldName) throws ClassNotFoundException, NoSuchFieldException {
        return getField(Class.forName(className), fieldName);
    }

    /**
     * 获得字段的字节映射(反射对象)
     *
     * @param clz
     * @param fieldName
     * @return
     * @throws NoSuchFieldException
     */

    public static Field getField(Class<?> clz, String fieldName) throws NoSuchFieldException {
        return clz.getDeclaredField(fieldName);
    }

    /**
     * 获得字段的值
     *
     * @param instance
     * @param className
     * @param fieldName
     * @return
     * @throws NoSuchFieldException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     */
    public static Object getFieldValue(Object instance, String className, String fieldName) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        return getFieldValue(instance, getField(className, fieldName));
    }

    /**
     * 获得字段的值
     *
     * @param instance
     * @param field
     * @return
     * @throws NoSuchFieldException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     */
    public static Object getFieldValue(Object instance, Field field) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        field.setAccessible(true);
        return field.get(instance);
    }

    public static void setFieldValue(Object instance, Field field, Object value) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        field.setAccessible(true);
        field.set(instance, value);
    }

    /**
     * 获得方法的字节映射(反射对象)
     *
     * @param className
     * @param methodName
     * @param paramClzs
     * @return
     * @throws NoSuchMethodException
     * @throws ClassNotFoundException
     */
    public static Method getMethod(String className, String methodName, Class<?>... paramClzs) throws NoSuchMethodException, ClassNotFoundException {
        return getMethod(Class.forName(className), methodName, paramClzs);
    }

    /**
     * 获得方法的字节映射(反射对象)
     *
     * @param clz
     * @param methodName
     * @param paramClzs
     * @return
     * @throws NoSuchMethodException
     */
    public static Method getMethod(Class<?> clz, String methodName, Class<?>... paramClzs) throws NoSuchMethodException {
        return clz.getDeclaredMethod(methodName, paramClzs);
    }

    /**
     * 调用方法
     *
     * @param instance
     * @param method
     * @param params
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object invokeMethod(Object instance, Method method, Object... params) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        method.setAccessible(true);
        return method.invoke(instance, params);
    }

    /**
     * 拷贝名字相同的属性
     *
     * @param sourceObj
     * @param targetObj
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static boolean copySameField(Object sourceObj, Object targetObj){
        boolean copySuccess = true;
        Field[] sourceFields = sourceObj.getClass().getDeclaredFields();
        Field[] targetFields = targetObj.getClass().getDeclaredFields();
        outloop:
        for (Field sourceField : sourceFields) {
            for (Field targetField : targetFields) {
                if (sourceField.getName().equals(targetField.getName())) {
                    try {
                        sourceField.setAccessible(true);
                        targetField.setAccessible(true);
                        targetField.set(targetObj, sourceField.get(sourceObj));//复制字段值
                    } catch (Exception e) {
                        copySuccess = false;
                        LogUtil.w(TAG, "copySameField() called with: sourceObj = [" + sourceObj + "], targetObj = [" + targetObj + "]", e);
                    }
                    continue outloop;
                }
            }
        }
        return copySuccess;
    }

    /**
     * 拷贝相似属性
     *
     * @param sourceObj
     * @param targetObj
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static boolean copySimilarField(Object sourceObj, Object targetObj){
        boolean copySuccess = true;
        Field[] sourceFields = sourceObj.getClass().getDeclaredFields();
        Field[] targetFields = targetObj.getClass().getDeclaredFields();
        outloop:
        for (Field sourceField : sourceFields) {
            for (Field targetField : targetFields) {
                if (sourceField.getName().replace("_", "").equalsIgnoreCase(targetField.getName().replace("_", ""))) {
                    try {
                        sourceField.setAccessible(true);
                        targetField.setAccessible(true);
                        targetField.set(targetObj, sourceField.get(sourceObj));//复制字段值
                    } catch (Exception e) {
                        copySuccess = false;
                        LogUtil.w(TAG, "copySimilarField() called with: sourceObj = [" + sourceObj + "], targetObj = [" + targetObj + "]", e);
                    }
                    continue outloop;
                }
            }
        }
        return copySuccess;
    }
}
