# Utilities (Java)
## Java library containing various java utility methods


### Utilities:

 * Reflection
 * String
 * LocalDateTime
 * Stream
 * Function, Consumer, Supplier

## Installation


### 1.) Add repository to build.gradle

```

    maven {
        url  "https://dl.bintray.com/shocknode/mvn"
    }

```
### 2.) Import library:
```

	compile 'com.shocknode:utilities:0.0.1'

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

public class IntegerTests {
    public static void main(String[] args) throws Exception {

        Objects objects = Reflection.instanceOf(Objects.class);

        assert Reflection.isInstanceOf(objects, Objects.class);
        assert Reflection.isInstanceOf(Reflection.instanceOf(Objects.class), Objects.class);

        assert Reflection.isInstanceOf(objects.getIntegerObject().getClass(), Integer.class);
        assert !Reflection.isInstanceOf(objects.getIntegerObject().getClass(), Long.class, Double.class, Float.class);

        assert Reflection.isType(Reflection.getField(objects,"integerObject"), TypeName.Objects.INTEGER);
        assert !Reflection.isType(Reflection.getField(objects,"integerObject"), TypeName.Objects.DOUBLE, TypeName.Primatives.INT);

        assert Reflection.isTypeOfAny(Reflection.getField(objects,"integerObject"), TypeName.Objects.INTEGER, TypeName.Primatives.BOOLEAN);
        assert !Reflection.isTypeOfAny(Reflection.getField(objects,"integerObject"), TypeName.Objects.DOUBLE, TypeName.Primatives.INT);

        {
            Exception exception = null;
            try { Reflection.runSetter(objects, "integer", true); }
            catch (NoSuchMethodException e) { exception = e; }
            if(exception==null){ Assert.fail("exception expected"); }
        }

        {
            Exception exception = null;
            try { Reflection.runGetter(objects, "integer", boolean.class); }
            catch (NoSuchFieldException e) { exception = e; }
            if(exception==null){ Assert.fail("exception expected"); }
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

    //TODO:

```

### Streams
```java

    //TODO:

```