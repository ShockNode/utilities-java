package com.shocknode.utilities.reflection;

import java.util.HashMap;
import java.util.Map;

public class InstanceBuilder {

    private Object object;
    private Map<Class<?>, Object> parameters;

    public void reset(){
        object = new Object();
        parameters = new HashMap<>();
    }

    public InstanceBuilder object(Object object){
        this.object = object;
        return this;
    }

    public <T> InstanceBuilder parameter(Class<T> clazz, T parameter){
        parameters.put(clazz, parameter);
        return this;
    }

}
