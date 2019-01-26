package com.shocknode.utilities.reflection;

import java.lang.reflect.*;
import java.util.*;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.shocknode.utilities.Strings.capitalize;


public class Reflection {


    /**
     *
     *  runs getter method for a specified field name
     *
     * @author              brentlrayjr
     *
     * @param object        object to run getter method on
     *
     * @param fieldName     name of the field to run getter method on
     *
     * @param fieldClass    class type of the value returned from the function
     *
     * @return value of class type provided as the last parameter of the function
     *
     *
     *
    **/
    @SuppressWarnings("unchecked")
    public static <T> T runGetter(Object object, String fieldName, Class<T> fieldClass) throws IllegalAccessException, InvocationTargetException, NullNotSupportedException, NoSuchFieldException, NoSuchMethodException {

        if(object == null){
            throw new NullNotSupportedException();
        }

        String name = isTypeOfAny(getField(object, fieldName), TypeName.BOOLEAN, TypeName.BOOLEAN_WRAPPER) ? String.format("is%s", capitalize(fieldName)):String.format("get%s", capitalize(fieldName));

        Method method = getMethod(object.getClass(), name);

        Object value = method.invoke(object);

        if(fieldClass.isInstance(value))
        return (T) value;

        throw new NoSuchMethodException(fieldName);
    }

    /**
     *
     *  returns an instance of the class type provided
     *
     * @author              brentlrayjr
     *
     * @param objectClass        class type to return instance of
     *
     * @param parameters    map of field class type and value representing a parameter
     *
     * @return instance of class type provided as the first parameter of the function
     *
     **/
    public static <T> T instanceOf(Class<T> objectClass, Parameter... parameters) throws InstantiationException, IllegalAccessException, InvocationTargetException, NullNotSupportedException {

        if(objectClass == null){ throw new NullNotSupportedException(); }

        if(parameters!= null) {

            for (Parameter parameter : parameters) {
                if (parameter.getParameterClass() == null || parameter.getValue() == null) {
                    throw new NullNotSupportedException();
                }
            }

            Constructor<T> constructor = getConstructor(objectClass, Arrays.stream(parameters)
                    .map(Parameter::getParameterClass)
                    .collect(Collectors.toList())
                    .toArray(new Class<?>[]{}));

            return constructor.newInstance(Arrays.stream(parameters)
                    .map(Parameter::getValue)
                    .toArray());

        }

        Constructor<T> constructor = getConstructor(objectClass);
        return constructor.newInstance();

    }

    /**
     *
     *  returns true if object is an instance of all provided classes, else false
     *
     * @author              brentlrayjr
     *
     * @param object        object to run instance check on
     *
     * @param classes    list of classes to check object against
     *
     * @return true if object is an instance of all provided classes, else false
     *
     **/
    public static <T> boolean isInstanceOf(T object, Class<?>... classes){
        return isInstanceOf(object.getClass(), classes);
    }

    /**
     *
     *  returns true if object is an instance of all provided classes, else false
     *
     * @author              brentlrayjr
     *
     * @param clazz        class to run instance check on
     *
     * @param classes    list of classes to check object against
     *
     * @return true if object is an instance of all provided classes, else false
     *
     **/
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

    /**
     *
     *  returns true if object is an instance of any provided classes, else false
     *
     * @author              brentlrayjr
     *
     * @param object        object to run instance check on
     *
     * @param classes    list of classes to check object against
     *
     * @return true if object is an instance of any provided classes, else false
     *
     **/
    public static <T> boolean isInstanceOfAny(T object, Class<?>... classes){
        return isInstanceOfAny(object.getClass(), classes);
    }


    /**
     *
     *  returns true if object is an instance of any provided classes, else false
     *
     * @author              brentlrayjr
     *
     * @param clazz        class to run instance check on
     *
     * @param classes    list of classes to check object against
     *
     * @return true if object is an instance of any provided classes, else false
     *
     **/
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

    /**
     *
     *  returns the specified method of the provided object with the provided parameter
     *
     * @author              brentlrayjr
     *
     * @param object       object to run getter method on
     *
     * @param parameters    map of field class type and value representing a parameter
     *
     * @return instance of class type provided as the first parameter of the function
     *
     **/
    public static Object run(Object object, String name, Parameter... parameters) throws Exception {

        if(object == null){
            return null;
        }

        if(parameters!= null) {

            for (Parameter parameter : parameters) {
                if (parameter.getParameterClass() == null || parameter.getValue() == null) {
                    throw new NullNotSupportedException();
                }
            }

            Method method = getMethod(object.getClass(), name, Arrays.stream(parameters)
                    .map(Parameter::getParameterClass)
                    .collect(Collectors.toList())
                    .toArray(new Class<?>[]{}));

            return method.invoke(object, Arrays.stream(parameters)
                    .map(Parameter::getValue)
                    .toArray());

        }

        Method method = getMethod(object.getClass(), name);
        return method.invoke(object);

    }

    /**
     *
     *  runs setter method for the provided field name with the provided parameter
     *
     * @author              brentlrayjr
     *
     * @param object        object to run setter method on
     *
     * @param fieldName     name of the field to run setter method on
     *
     * @param parameter    parameter to provide to the setter method
     *
     * @see Parameter
     *
     **/
    public static void runSetter(Object object, String fieldName, Parameter<?> parameter) throws NullNotSupportedException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if(object == null){ return; }
        String name = String.format("set%s", capitalize(fieldName));
        Method method = getMethod(object.getClass(), name, parameter.getParameterClass());
        method.invoke(object, parameter.getValue());
    }

    /**
     *
     *  runs setter method for the provided field name with the provided value
     *
     * @author              brentlrayjr
     *
     * @param object        object to run setter method on
     *
     * @param fieldName     name of the field to run setter method on
     *
     * @param value    value to provide to the setter method
     *
     **/
    @SuppressWarnings("unchecked")
    public static void runSetter(Object object, String fieldName, Object value) throws NullNotSupportedException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        runSetter(object, fieldName, Parameter.from((Class<? super Object>) value.getClass(), value));
    }

    /**
     *
     *  returns true if field type matches any provided type strings, else false
     *
     * @author              brentlrayjr
     *
     * @param field        field to run type check on
     *
     * @param types    list of type strings to check field against
     *
     * @return true if field type matches any provided type strings, else false
     *
     **/
    public static boolean isTypeOfAny(Field field, String... types){
        if(field == null){ return false; }
        for(String type: types){
            if(field.getType().getTypeName().equals(type)){
                return true;
            }
        }
        return false;
    }

    /**
     *
     *  returns true if field type matches all provided type strings, else false
     *
     * @author              brentlrayjr
     *
     * @param field        field to run type check on
     *
     * @param types    list of type strings to check field against
     *
     * @return true if field type matches all provided type strings, else false
     *
     **/
    public static boolean isType(Field field, String... types){
        if(field == null){ return false; }
        for(String type: types){
            if(!field.getType().getTypeName().equals(type)){
                return false;
            }
        }
        return true;
    }

    /**
     *
     *  returns the name of the provided field object
     *
     * @author              brentlrayjr
     *
     * @param field       field to return name from
     *
     * @return the name of the provided field object
     *
     **/
    public static String getName(Field field){
        if(field == null){ return ""; }
        return field.getName();
    }

    /**
     *
     *  returns the type string of the provided field object
     *
     * @author              brentlrayjr
     *
     * @param field       field to return name from
     *
     * @return the type string of the provided field object
     *
     **/
    public static String getType(Field field){
        if(field == null){ return ""; }
        return field.getType().getTypeName();
    }

    /**
     *
     *  returns the type string of the provided object
     *
     * @author              brentlrayjr
     *
     * @param value       object to return name from
     *
     * @return the type string of the provided object
     *
     **/
    public static String getType(Object value){
        if(value == null){ return ""; }
        return value.getClass().getTypeName();
    }

    /**
     *
     *  returns the field of the provided object
     *
     * @author              brentlrayjr
     *
     * @param object       object to return field from
     *
     * @param name    name of the field to return
     *
     * @return field found by provided name
     *
     **/
    public static Field getField(Object object, String name) throws NullNotSupportedException, NoSuchFieldException {

        if(object==null){ throw new NullNotSupportedException(); }

        for(Field field: getHierarchyFields(object.getClass(), Object.class)){
            if(field.getName().equals(name)){
                return field;
            }
        }
        throw new NoSuchFieldException();
    }

    /**
     *
     *  returns the method of the provided class
     *
     * @author              brentlrayjr
     *
     * @param clazz       class that contains the method
     *
     * @param name name of method to return
     *
     * @param parameters    array of classes representing method parameters
     *
     * @return method found by provided name and parameter classes
     *
     **/
    public static Method getMethod(Class<?> clazz, String name, Class<?>... parameters) throws NullNotSupportedException, NoSuchMethodException {

        if(clazz==null){ return null; }

        for(Class<?> parameter: parameters){ if(parameter==null){ throw new NullNotSupportedException(); } }

        try { return clazz.getDeclaredMethod(name, parameters); }
        catch (NoSuchMethodException e) {
            Class<?> parent = clazz.getSuperclass();
            if(parent == null){throw e;}
            return getMethod(parent, name, parameters);
        }

    }

    /**
     *
     *  returns the constructor of the provided class
     *
     * @author              brentlrayjr
     *
     * @param clazz       class to return constructor from
     *
     * @param parameters    array of classes representing parameters
     *
     * @return constructor found by provided name and parameter classes
     *
     **/

    public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... parameters){

        if(clazz==null){ return null; }

        for(Class<?> parameter: parameters){ if(parameter==null){ return null; } }

        try { return clazz.getDeclaredConstructor(parameters); }
        catch (NoSuchMethodException e) {
            return null;
        }

    }

    /**
     *
     *  returns a list of fields found up the class hierarchy until end class is reached
     *
     * @author              brentlrayjr
     *
     * @param start       class to start collecting fields from
     *
     * @param end    last class to collecting fields from
     *
     * @return list of fields found up the class hierarchy until end class is reached
     *
     **/
    public static List<Field> getHierarchyFields(Class<?> start, Class<?> end) {

        if(start == null){ return Collections.emptyList(); }

        List<Field> fields = new ArrayList<>();
        Class<?> parent = start.getSuperclass();

        if (parent != null && !parent.equals(Object.class) &&!(parent.equals(end))) {
            fields.addAll(getHierarchyFields(parent, end));
        }

        fields.addAll(Arrays.asList(start.getDeclaredFields()));

        return fields;
    }

    /**
     *
     *  returns a list of fields found up the class hierarchy
     *
     * @author              brentlrayjr
     *
     * @param start       class to start collecting fields from
     *
     * @return list of fields found
     *
     **/
    public static List<Field> getHierarchyFields(Class<?> start) {
        if(start == null){ return Collections.emptyList(); }
        return getHierarchyFields(start, null);
    }

    /**
     *
     *  returns a list of fields found up the object's class hierarchy
     *
     * @author              brentlrayjr
     *
     * @param start       object to start collecting fields from
     *
     * @return list of fields found
     *
     **/
    public static List<Field> getHierarchyFields(Object start) {
        if(start == null){ return Collections.emptyList(); }
        return getHierarchyFields(start.getClass(), null);
    }

    /**
     *
     *  returns a list of fields found up the start object's class hierarchy until the end class is reached
     *
     * @author              brentlrayjr
     *
     * @param start       class to start collecting fields from
     *
     * @param end    last class to collecting fields from
     *
     * @return list of fields found up the object's class hierarchy until end class is reached
     *
     **/
    public static List<Field> getHierarchyFields(Object start, Class<?> end) {
        if(start == null){ return Collections.emptyList(); }
        return getHierarchyFields(start.getClass(), end);
    }

    /**
     *
     *  returns a list of methods found up the start class hierarchy until end class is reached
     *
     * @author              brentlrayjr
     *
     * @param start       class to start collecting methods from
     *
     * @param end    last class to collecting methods from
     *
     * @return list of methods found up the class hierarchy until end class is reached
     *
     **/
    public static List<Method> getHierarchyMethods(Class<?> start, Class<?> end) {

        if(start == null){ return Collections.emptyList(); }

        List<Method> methods = new ArrayList<>();
        Class<?> parent = start.getSuperclass();

        if (parent != null && !(parent.equals(end))) {
            methods.addAll(getHierarchyMethods(parent, end));
        }

        methods.addAll(Arrays.asList(start.getDeclaredMethods()));

        return methods;
    }

    /**
     *
     *  returns a list of methods found up the start class hierarchy
     *
     * @author              brentlrayjr
     *
     * @param start       class to start collecting methods from
     *
     * @return list of methods found up the class hierarchy
     *
     **/
    public static List<Method> getHierarchyMethods(Class<?> start) {
        if(start == null){ return Collections.emptyList(); }
        return getHierarchyMethods(start, null);
    }

    /**
     *
     *  returns a list of methods found up the start object's class hierarchy
     *
     * @author              brentlrayjr
     *
     * @param start       object to start collecting methods from
     *
     * @return list of methods found up the object's class hierarchy
     *
     **/
    public static List<Method> getHierarchyMethods(Object start) {
        if(start == null){ return Collections.emptyList(); }
        return getHierarchyMethods(start.getClass(), null);
    }

    /**
     *
     *  returns a list of methods found up the start object's class hierarchy until the end class is reached
     *
     * @author              brentlrayjr
     *
     * @param start       object to start collecting methods from
     *
     * @param end         class to end collection of methods after
     *
     * @return list of methods found up the start object's class hierarchy until the end class is reached
     *
     **/
    public static List<Method> getHierarchyMethods(Object start, Class<?> end) {
        if(start == null){ return Collections.emptyList(); }
        return getHierarchyMethods(start.getClass(), end);
    }


    /**
     *
     *  returns a list of constructors found up the start class hierarchy until the end class is reached
     *
     * @author              brentlrayjr
     *
     * @param start       class to start collecting constructors from
     *
     * @param end         class to end collection of methods after
     *
     * @return list of constructors found up the start class hierarchy until the end class is reached
     *
     **/
    public static List<Constructor> getHierarchyConstructors(Class<?> start, Class<?> end) {

        if(start == null){ return Collections.emptyList(); }


        List<Constructor> constructors = new ArrayList<>();


        if(end != null) {

            Class<?> parent = start.getSuperclass();

            if (parent != null) {

                if (parent.equals(end)) {
                    end = null;
                }

                constructors.addAll(getHierarchyConstructors(parent, end));
            }
        }else

        constructors.addAll(Arrays.asList(start.getDeclaredConstructors()));

        return constructors;
    }


    /**
     *
     *  returns a list of constructors found up the start class hierarchy
     *
     * @author              brentlrayjr
     *
     * @param start       class to start collecting constructors from
     *
     * @return list of constructors found up the start class hierarchy
     *
     **/
    public static List<Constructor> getHierarchyConstructors(Class<?> start) {
        if(start == null){ return Collections.emptyList(); }
        return getHierarchyConstructors(start, null);
    }

    /**
     *
     *  returns a list of constructors found up the start object's class hierarchy
     *
     * @author              brentlrayjr
     *
     * @param start       object to start collecting constructors from
     *
     * @return list of constructors found up the start object's class hierarchy
     *
     **/
    public static List<Constructor> getHierarchyConstructors(Object start) {
        if(start == null){ return Collections.emptyList(); }
        return getHierarchyConstructors(start.getClass(), null);
    }

    /**
     *
     *  returns a list of constructors found up the start object's class hierarchy until the end class is reached
     *
     * @author              brentlrayjr
     *
     * @param start       object to start collecting constructors from
     *
     * @param end         class to end collection of methods after
     *
     * @return list of constructors found up the start object's class hierarchy until the end class is reached
     *
     **/
    public static List<Constructor> getHierarchyConstructors(Object start, Class<?> end) {
        if(start == null){ return Collections.emptyList(); }
        return getHierarchyConstructors(start.getClass(), end);
    }

    /**
     *
     *  returns a list of constructors found up the start object's class hierarchy
     *
     * @author              brentlrayjr
     *
     * @param object       object to retrieve list from
     *
     * @param name       name of list field
     *
     * @return class of list field
     *
     **/
    public static Class<?> getListClass(Object object, String name) throws Exception {

        System.out.println(object.getClass());
        Field listField = getField(object, name);
        if(!listField.getType().equals(List.class)){
            System.out.println("1");
            //TODO:
            throw new Exception("Not a list!");

         }

        ParameterizedType listType = (ParameterizedType) listField.getGenericType();
        Type[] classes = listType.getActualTypeArguments();
        if(classes.length > 0){ return (Class<?>)classes[0]; }

        //TODO:
        throw new Exception("No type parameter of list!!");

    }


    /**
     *
     *  returns a list of constructors found up the start object's class hierarchy
     *
     * @author              brentlrayjr
     *
     * @param object       object to retrieve list from
     *
     * @param name       name of list field
     *
     * @param listTypeClass       class type of list to return
     *
     * @return list of provided class type
     *
     **/
    @SuppressWarnings("unchecked")
    public static <T> List<T> getList(Object object, String name, Class<T> listTypeClass) throws Exception {
        getListClass(object, name);
        Field field = getField(object, name);

        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        List<T> list = (List<T>) field.get(object);
        field.setAccessible(accessible);
        return list;

    }

    private static void whileAccessible(Field field, Consumer<Field> consumer) throws Exception {

        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        consumer.accept(field);
        field.setAccessible(accessible);

    }

    }
