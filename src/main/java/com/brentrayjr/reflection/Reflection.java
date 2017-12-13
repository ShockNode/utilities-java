package com.brentrayjr.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Reflection {

    static class TypeName {

        static class Primatives {

            public static final String INT = "int";
            public static final String DOUBLE = "double";
            public static final String FLOAT = "float";
            public static final String BOOLEAN = "boolean";
            public static final String SHORT = "short";
            public static final String LONG = "long";
            public static final String CHAR = "char";
            public static final String BYTE = "byte";

        }

        static class Objects {

            public static final String STRING = "java.lang.String";
            public static final String INTEGER = "java.lang.Integer";
            public static final String DOUBLE = "java.lang.Double";
            public static final String FLOAT = "java.lang.Float";
            public static final String BOOLEAN = "java.lang.Boolean";
            public static final String DATETIME = "java.time.LocalDateTime";

        }

    }



    public static Object runGetter(Object object, String fieldName) throws IllegalAccessException, InvocationTargetException {

        String name = (Reflection.isType(getField(object, fieldName), TypeName.Objects.BOOLEAN)) ? String.format("is%s", capitalize(fieldName)):String.format("get%s", capitalize(fieldName));

        Method method = getMethod(object.getClass(), name);
        return method.invoke(object);
    }

    public static <O> O instanceOf(Class<O> objectClass, Object... values) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        Class[] constructorClasses = Arrays.stream(values).map(Object::getClass).toArray(Class[]::new);

        Constructor<O> constructor = objectClass.getConstructor(constructorClasses);
        return constructor.newInstance(values);

    }

    public static boolean isInstanceOf(Class<?> clazz, Class<?>... classes){

        for(Class<?> superclass: classes){
            if(!superclass.isAssignableFrom(clazz)){
                return false;
            }
        }

        return true;
    }

    public static boolean isInstanceOfAny(Class<?> clazz, Class<?>... classes){

        for(Class<?> superclass: classes){
            if(superclass.isAssignableFrom(clazz)){
                return true;
            }
        }

        return false;
    }

    public static Object run(Object object, String name, Object... values) throws Exception {

        name = capitalize(name);
        Class[] methodClasses = Arrays.stream(values).map(Object::getClass).toArray(Class[]::new);
        Method method = getMethod(object.getClass(), name, methodClasses);
        return method.invoke(object, values);

    }

    public static <O, V> Object runSetter(O object, String fieldName, V value){

        String name = String.format("set%s", capitalize(fieldName));

        Method method = getMethod(object.getClass(), name, value.getClass());
        try { return method.invoke(object, (V) value); }
        catch (IllegalAccessException|InvocationTargetException e){ return null; }

    }

    public static boolean isType(Field field, String... types){
        for(String type: types){
            if(field.getType().getTypeName().equals(type)){
                return true;
            }
        }
        return false;
    }

    public static String getName(Field field){
        return field.getName();
    }

    public static String getType(Field field){
        return field.getType().getTypeName();
    }

    public static String getType(Object value){
        return value.getClass().getTypeName();
    }

    public static Field getField(Object object, String name, Class<?>... parameters){

        for(Field field: getHierarchyFields(object.getClass(), Object.class)){
            if(field.getName().equals(name)){
                return field;
            }
        }

        return null;

    }

    public static Method getMethod(Class<?> clazz, String name, Class<?>... parameters){

        try { return clazz.getDeclaredMethod(name, parameters); }
        catch (NoSuchMethodException e) {
            Class<?> parent = clazz.getSuperclass();
            if(parent == null){return null;}
            return getMethod(parent, name, parameters);
        }

    }

    public static List<Field> getHierarchyFields(Class<?> start, Class<?> end) {

        List<Field> fields = new ArrayList<Field>();
        Class<?> parent = start.getSuperclass();

        if (parent != null && (end == null || !(parent.equals(end)))) {
            fields.addAll(getHierarchyFields(parent, end));
        }

        fields.addAll(Arrays.asList(start.getDeclaredFields()));

        return fields;
    }

    public static List<Field> getHierarchyFields(Class<?> start) {

        return getHierarchyFields(start, null);
    }

    public static List<Field> getHierarchyFields(Object start) {

        return getHierarchyFields(start.getClass(), null);
    }

    public static List<Field> getHierarchyFields(Object start, Class<?> end) {

        return getHierarchyFields(start.getClass(), end);
    }


    public static List<Method> getHierarchyMethods(Class<?> start, Class<?> end) {

        List<Method> methods = new ArrayList<>();
        Class<?> parent = start.getSuperclass();

        if (parent != null && (end == null || !(parent.equals(end)))) {
            methods.addAll(getHierarchyMethods(parent, end));
        }

        methods.addAll(Arrays.asList(start.getDeclaredMethods()));

        return methods;
    }

    public static List<Method> getHierarchyMethods(Class<?> start) {

        return getHierarchyMethods(start, null);
    }

    public static List<Method> getHierarchyMethods(Object start) {

        return getHierarchyMethods(start.getClass(), null);
    }

    public static List<Method> getHierarchyMethods(Object start, Class<?> end) {

        return getHierarchyMethods(start.getClass(), end);
    }


    private static String capitalize(String original){
        return original.length() == 0 ? original : original.substring(0, 1).toUpperCase() + original.substring(1);
    }

}
