import com.brentrayjr.butils.NullNotSupportedException;
import com.brentrayjr.butils.Reflection;
import com.brentrayjr.butils.TypeName;

public class Suite {

    public Suite(){}

    public static void main(String[] args) throws Exception {

        Suite suite = new Suite();
        suite.integerObjectTest();
        suite.doubleObjectTest();

    }

    public void integerObjectTest() throws Exception {

        Objects objects = new Objects();

        assert Reflection.isInstanceOf(objects, Objects.class);
        assert Reflection.isInstanceOf(Reflection.instanceOf(Objects.class, null), Objects.class);

        assert Reflection.isInstanceOf(objects.getIntegerObject().getClass(), Integer.class);
        assert !Reflection.isInstanceOf(objects.getIntegerObject().getClass(), Long.class, Double.class, Float.class);

        assert Reflection.isType(Reflection.getField(objects,"integerObject"), TypeName.Objects.INTEGER);
        assert !Reflection.isType(Reflection.getField(objects,"integerObject"), TypeName.Objects.DOUBLE, TypeName.Primatives.INT);

        assert Reflection.isTypeOfAny(Reflection.getField(objects,"integerObject"), TypeName.Objects.INTEGER, TypeName.Primatives.BOOLEAN);
        assert !Reflection.isTypeOfAny(Reflection.getField(objects,"integerObject"), TypeName.Objects.DOUBLE, TypeName.Primatives.INT);

        try { Reflection.runSetter(objects, "integer", 1); }
        catch (NoSuchMethodException e){
            System.out.println("NoSuchMethodException caught!");
        }

        try { Reflection.runGetter(objects, "integer", true); }
        catch (NoSuchFieldException e){
            System.out.println("NoSuchFieldException caught!");
        }

        try { Reflection.runGetter(null, "integer", true); }
        catch (NullNotSupportedException e){
            System.out.println("NullNotSupportedException caught!");
        }

        Reflection.runSetter(objects, "integerObject", 1);
        assert Reflection.runGetter(objects, "integerObject", true).equals(1);

        Reflection.run(objects, "setIntegerObject", new Class[]{Integer.class}, 2);
        assert Reflection.run(objects, "getIntegerObject", null).equals(2);

    }

    public void doubleObjectTest() throws Exception {

        Objects objects = new Objects();

        assert Reflection.isInstanceOf(objects, Objects.class);
        assert Reflection.isInstanceOf(Reflection.instanceOf(Objects.class, null), Objects.class);

        assert Reflection.isInstanceOf(objects.getDoubleObject().getClass(), Double.class);
        assert !Reflection.isInstanceOf(objects.getDoubleObject().getClass(), Long.class, Double.class, Float.class);

        assert Reflection.isType(Reflection.getField(objects,"doubleObject"), TypeName.Objects.DOUBLE);
        assert !Reflection.isType(Reflection.getField(objects,"doubleObject"), TypeName.Primatives.DOUBLE);

        assert Reflection.isTypeOfAny(Reflection.getField(objects,"doubleObject"), TypeName.Objects.INTEGER, TypeName.Objects.DOUBLE);
        assert !Reflection.isTypeOfAny(Reflection.getField(objects,"doubleObject"), TypeName.Objects.INTEGER, TypeName.Primatives.DOUBLE);

        try { Reflection.runSetter(objects, "double", 1.0); }
        catch (NoSuchMethodException e){
            System.out.println("NoSuchMethodException caught!");
        }

        try { Reflection.runGetter(objects, "double", true); }
        catch (NoSuchFieldException e){
            System.out.println("NoSuchFieldException caught!");
        }

        try { Reflection.runGetter(null, "double", true); }
        catch (NullNotSupportedException e){
            System.out.println("NullNotSupportedException caught!");
        }

        Reflection.runSetter(objects, "doubleObject", 1.0);
        assert Reflection.runGetter(objects, "doubleObject", true).equals(1);

        Reflection.run(objects, "setDoubleObject", new Class[]{Double.class}, 2.0);
        assert Reflection.run(objects, "getDoubleObject", null).equals(2.0);

    }

    public void floatObjectTest() throws Exception {

        Objects objects = new Objects();

        assert Reflection.isInstanceOf(objects, Objects.class);
        assert Reflection.isInstanceOf(Reflection.instanceOf(Objects.class, null), Objects.class);

        assert Reflection.isInstanceOf(objects.getFloatObject().getClass(), Float.class);
        assert !Reflection.isInstanceOf(objects.getFloatObject().getClass(), Long.class, float.class, Float.class);

        assert Reflection.isType(Reflection.getField(objects,"floatObject"), TypeName.Objects.FLOAT);
        assert !Reflection.isType(Reflection.getField(objects,"floatObject"), TypeName.Objects.FLOAT, TypeName.Primatives.LONG);

        assert Reflection.isTypeOfAny(Reflection.getField(objects,"floatObject"), TypeName.Objects.FLOAT, TypeName.Objects.LONG);
        assert !Reflection.isTypeOfAny(Reflection.getField(objects,"floatObject"), TypeName.Primatives.LONG);

        try { Reflection.runSetter(objects, "float", 1F); }
        catch (NoSuchMethodException e){
            System.out.println("NoSuchMethodException caught!");
        }

        try { Reflection.runGetter(objects, "float", true); }
        catch (NoSuchFieldException e){
            System.out.println("NoSuchFieldException caught!");
        }

        try { Reflection.runGetter(null, "float", true); }
        catch (NullNotSupportedException e){
            System.out.println("NullNotSupportedException caught!");
        }

        Reflection.runSetter(objects, "floatObject", 1L);
        assert Reflection.runGetter(objects, "floatObject", true).equals(1F);

        Reflection.run(objects, "setFloatObject", new Class[]{Float.class}, 2F);
        assert Reflection.run(objects, "getFloatObject", null).equals(2F);

    }

}
