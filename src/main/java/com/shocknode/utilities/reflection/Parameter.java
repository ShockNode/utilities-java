package com.shocknode.utilities.reflection;

import java.util.Objects;

public class Parameter<T> {

    public static <T> Parameter from(Class<T> clazz, T value){
        Objects.requireNonNull(clazz);
        return new Parameter<T>(clazz, value);
    }

    private Class<T> parameterClass;
    private T value;

    private Parameter(Class<T> clazz, T value){
        this.parameterClass = clazz;
        this.value = value;
    }

    public Class<T> getParameterClass() {
        return parameterClass;
    }

    public T getValue() {
        return value;
    }

}
