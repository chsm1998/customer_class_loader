package com.chsm;

/**
 * @author chsmWork
 * @version v1.0
 * @since 2023/2/14 14:35
 */
public class CastTest {

    public static void main(String[] args) throws Exception {
        CustomClassLoader customClassLoader = new CustomClassLoader();
        Class<?> aClass = customClassLoader.loadClass("com.chsm.A");
        System.out.println(A.class.getClassLoader());
        System.out.println(aClass.getClassLoader());
        A a = (A) aClass.newInstance();
    }

}
