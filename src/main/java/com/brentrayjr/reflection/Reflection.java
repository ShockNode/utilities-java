package com.brentrayjr.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Reflection {

    public static class TypeName {

        public static class Primatives {

            public static final String INT = "int";
            public static final String DOUBLE = "double";
            public static final String FLOAT = "float";
            public static final String BOOLEAN = "boolean";
            public static final String SHORT = "short";
            public static final String LONG = "long";
            public static final String CHAR = "char";
            public static final String BYTE = "byte";

        }

        public static class Objects {

            public static final String STRING = "java.lang.String";
            public static final String INTEGER = "java.lang.Integer";
            public static final String DOUBLE = "java.lang.Double";
            public static final String FLOAT = "java.lang.Float";
            public static final String BOOLEAN = "java.lang.Boolean";
            public static final String TIME = "java.time.LocalTime";
            public static final String DATE = "java.time.LocalDate";
            public static final String DATETIME = "java.time.LocalDateTime";

        }

    }

    public static Object runGetter(Object object, String fieldName, boolean useIsForBoolean) throws IllegalAccessException, InvocationTargetException {

        if(object == null){
            return null;
        }

        String name = (useIsForBoolean&&isTypeOfAny(getField(object, fieldName), TypeName.Primatives.BOOLEAN, TypeName.Objects.BOOLEAN)) ? String.format("is%s", capitalize(fieldName)):String.format("get%s", capitalize(fieldName));

        Method method = getMethod(object.getClass(), name);
        return method.invoke(object);
    }

    public static <O> O instanceOf(Class<O> objectClass, Class<?>[] parameterClasses, Object... values) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, NullNotSupportedException {

        if(objectClass == null){
            return null;
        }

        if(parameterClasses != null){ for(Class<?> parameterClass: parameterClasses){ if(parameterClass==null){ throw new NullNotSupportedException(); } } }
        
        Constructor<O> constructor = (parameterClasses==null) ? getConstructor(objectClass):getConstructor(objectClass, parameterClasses);
        return (O) constructor.newInstance(values);

    }

    public static boolean isInstanceOf(Class<?> clazz, Class<?>... classes){

        for(Class<?> superclass: classes){

            if((clazz == null&&superclass==null)){
                return true;
            }

            if(superclass==null||clazz == null){
                return false;
            }

            if(!superclass.isAssignableFrom(clazz)){
                return false;
            }
        }

        return true;
    }

    public static boolean isInstanceOfAny(Class<?> clazz, Class<?>... classes){

        for(Class<?> superclass: classes){

            if((clazz == null&&superclass==null)){
                return true;
            }

            if(superclass==null||clazz == null){
                return false;
            }

            if(superclass.isAssignableFrom(clazz)){
                return true;
            }
        }


        return false;
    }

    public static Object run(Object object, String name, Class<?>[] parameterClasses, Object... values) throws Exception {

        if(object == null){
            return null;
        }

        if(parameterClasses != null){ for(Class<?> parameterClass: parameterClasses){ if(parameterClass==null){ throw new NullNotSupportedException(); } } }

        name = capitalize(name);
        Class[] methodClasses = Arrays.stream(values).map(Object::getClass).toArray(Class[]::new);
        Method method = getMethod(object.getClass(), name, methodClasses);
        return method.invoke(object, values);

    }

    public static <O, V> boolean runSetter(O object, String fieldName, V value){
        if(object == null){ return false; }
        String name = String.format("set%s", capitalize(fieldName));

        Method method = getMethod(object.getClass(), name, value.getClass());
        try { method.invoke(object, (V) value); return true; }
        catch (IllegalAccessException|InvocationTargetException e){ return false; }

    }

    public static boolean isTypeOfAny(Field field, String... types){
        if(field == null){ return false; }
        for(String type: types){
            if(field.getType().getTypeName().equals(type)){
                return true;
            }
        }
        return false;
    }

    public static boolean isType(Field field, String... types){
        if(field == null){ return false; }
        for(String type: types){
            if(!field.getType().getTypeName().equals(type)){
                return false;
            }
        }
        return true;
    }

    public static String getName(Field field){
        if(field == null){ return ""; }
        return field.getName();
    }

    public static String getType(Field field){
        if(field == null){ return ""; }
        return field.getType().getTypeName();
    }

    public static String getType(Object value){
        if(value == null){ return ""; }
        return value.getClass().getTypeName();
    }

    public static Field getField(Object object, String name, Class<?>... parameters){

        if(object==null){ return null; }

        for(Class<?> parameter: parameters){ if(parameter==null){ return null; } }

        for(Field field: getHierarchyFields(object.getClass(), Object.class)){
            if(field.getName().equals(name)){
                return field;
            }
        }

        return null;

    }

    public static Method getMethod(Class<?> clazz, String name, Class<?>... parameters){

        if(clazz==null){ return null; }

        for(Class<?> parameter: parameters){ if(parameter==null){ return null; } }

        try { return clazz.getDeclaredMethod(name, parameters); }
        catch (NoSuchMethodException e) {
            Class<?> parent = clazz.getSuperclass();
            if(parent == null){return null;}
            return getMethod(parent, name, parameters);
        }

    }

    public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... parameters){

        if(clazz==null){ return null; }

        for(Class<?> parameter: parameters){ if(parameter==null){ return null; } }

        try { return clazz.getDeclaredConstructor(parameters); }
        catch (NoSuchMethodException e) {
            return null;
        }

    }

    public static List<Field> getHierarchyFields(Class<?> start, Class<?> end) {

        if(start == null){ return Collections.emptyList(); }

        List<Field> fields = new ArrayList<>();
        Class<?> parent = start.getSuperclass();

        if (parent != null && (end == null || !(parent.equals(end)))) {
            fields.addAll(getHierarchyFields(parent, end));
        }

        fields.addAll(Arrays.asList(start.getDeclaredFields()));

        return fields;
    }

    public static List<Field> getHierarchyFields(Class<?> start) {
        if(start == null){ return Collections.emptyList(); }
        return getHierarchyFields(start, null);
    }

    public static List<Field> getHierarchyFields(Object start) {
        if(start == null){ return Collections.emptyList(); }
        return getHierarchyFields(start.getClass(), null);
    }

    public static List<Field> getHierarchyFields(Object start, Class<?> end) {
        if(start == null){ return Collections.emptyList(); }
        return getHierarchyFields(start.getClass(), end);
    }


    public static List<Method> getHierarchyMethods(Class<?> start, Class<?> end) {

        if(start == null){ return Collections.emptyList(); }

        List<Method> methods = new ArrayList<>();
        Class<?> parent = start.getSuperclass();

        if (parent != null && (end == null || !(parent.equals(end)))) {
            methods.addAll(getHierarchyMethods(parent, end));
        }

        methods.addAll(Arrays.asList(start.getDeclaredMethods()));

        return methods;
    }

    public static List<Method> getHierarchyMethods(Class<?> start) {
        if(start == null){ return Collections.emptyList(); }
        return getHierarchyMethods(start, null);
    }

    public static List<Method> getHierarchyMethods(Object start) {
        if(start == null){ return Collections.emptyList(); }
        return getHierarchyMethods(start.getClass(), null);
    }

    public static List<Method> getHierarchyMethods(Object start, Class<?> end) {
        if(start == null){ return Collections.emptyList(); }
        return getHierarchyMethods(start.getClass(), end);
    }
    

    public static List<Constructor> getHierarchyConstructors(Class<?> start, Class<?> end) {

        if(start == null){ return Collections.emptyList(); }

        List<Constructor> constructors = new ArrayList<>();
        Class<?> parent = start.getSuperclass();

        if (parent != null && (end == null || !(parent.equals(end)))) {
            constructors.addAll(getHierarchyConstructors(parent, end));
        }

        constructors.addAll(Arrays.asList(start.getDeclaredConstructors()));

        return constructors;
    }

    public static List<Constructor> getHierarchyConstructors(Class<?> start) {
        if(start == null){ return Collections.emptyList(); }
        return getHierarchyConstructors(start, null);
    }

    public static List<Constructor> getHierarchyConstructors(Object start) {
        if(start == null){ return Collections.emptyList(); }
        return getHierarchyConstructors(start.getClass(), null);
    }

    public static List<Constructor> getHierarchyConstructors(Object start, Class<?> end) {
        if(start == null){ return Collections.emptyList(); }
        return getHierarchyConstructors(start.getClass(), end);
    }


    private static String capitalize(String original){
        return original.length() == 0 ? original : original.substring(0, 1).toUpperCase() + original.substring(1);
    }

}
