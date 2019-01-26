
import com.shocknode.utilities.reflection.NullNotSupportedException;
import com.shocknode.utilities.reflection.Parameter;
import com.shocknode.utilities.reflection.Reflection;
import com.shocknode.utilities.reflection.TypeName;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;


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
