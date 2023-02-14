package chsm;

import com.chsm.C;
import com.chsm.CustomClassLoader;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author chsmWork
 * @version v1.0
 * @since 2023/2/14 8:20
 */
public class Test {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, FileNotFoundException {
        CustomClassLoader customClassLoader = new CustomClassLoader();
        Class<?> aClass = customClassLoader.loadClass("com.chsm.A");
        Object o = aClass.newInstance();
        Method out = aClass.getMethod("out");
        out.invoke(o);
        CustomClassLoader bCustomClassLoader = new CustomClassLoader();
        Class<?> bClass = bCustomClassLoader.loadClass("com.chsm.B");
        Object b = bClass.newInstance();
        Method bMethod = bClass.getMethod("out");
        bMethod.invoke(b);
    }

}
