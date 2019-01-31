# Utilities (Java)
## Java library containing various java utility methods


### Utilities:

 * Reflection
 * String
 * LocalDateTime
 * Stream
 * Function, Consumer, Supplier

## Installation

### 1.) Import library:
```

	compile 'com.shocknode:utilities:0.0.6'

```

## Usage

### Reflection:

```java

public class Objects {

    private Integer integerObject;

    public Objects() {

        this.integerObject = 0;

    }

    public Integer getIntegerObject() {
        return integerObject;
    }

    public void setIntegerObject(Integer integerObject) {
        this.integerObject = integerObject;
    }

}

```

```java

public class Suite {

    public Suite(){}

    public static void main(String[] args) throws Exception {

        Suite suite = new Suite();
        suite.integerTests();


    }

    private void integerTests() throws Exception {

        Objects objects = Reflection.instanceOf(Objects.class);

        assert Reflection.isInstanceOf(objects, Objects.class);
        assert Reflection.isInstanceOf(Reflection.instanceOf(Objects.class), Objects.class);

        assert Reflection.isInstanceOf(objects.getIntegerObject().getClass(), Integer.class);
        assert !Reflection.isInstanceOf(objects.getIntegerObject().getClass(), Long.class, Double.class, Float.class);

        assert Reflection.isType(Reflection.getField(objects,"integerObject"), TypeName.INTEGER);
        assert !Reflection.isType(Reflection.getField(objects,"integerObject"), TypeName.DOUBLE_WRAPPER, TypeName.INT);

        assert Reflection.isTypeOfAny(Reflection.getField(objects,"integerObject"), TypeName.INTEGER, TypeName.BOOLEAN);
        assert !Reflection.isTypeOfAny(Reflection.getField(objects,"integerObject"), TypeName.DOUBLE_WRAPPER, TypeName.INT);

        {
            Exception exception = null;
            try { Reflection.runSetter(objects, "integer", true); }
            catch (NoSuchMethodException e) { exception = e; }
            if(exception==null){ throw new Exception("exception expected"); }
        }

        {
            Exception exception = null;
            try { Reflection.runGetter(objects, "integer", boolean.class); }
            catch (NoSuchFieldException e) { exception = e; }
            if(exception==null){ throw new Exception("exception expected"); }
        }

        Reflection.runSetter(objects, "integerObject",  Parameter.from(Integer.class, 1));
        assert Reflection.runGetter(objects, "integerObject", Integer.class).equals(1);

        Reflection.run(objects, "setIntegerObject", Parameter.from(Integer.class, 2));
        assert Reflection.run(objects, "getIntegerObject").equals(2);

    }

}

```

### Strings

```java

	//TODO:

```
### DateTime
```java
    
	//TODO:

```
### Lambdas
```java

    public void lambdas() throws Exception {
    
        Arrays.asList("1", "2", "3")
        .stream()
        .map(Integer::valueOf)
        .forEach(consumeWithException(this::except));
    
    }
    
    public void except(Integer integer) throws Exception{
        //do something
    }

```

### Streams
```java

    //TODO:

```