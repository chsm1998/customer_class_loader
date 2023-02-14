package com.chsm;

import java.lang.reflect.Method;

/**
 * @author chsmWork
 * @version v1.0
 * @since 2023/2/14 10:35
 */
public class ReRun {

    public static boolean flag = true;

    public static void setFlag() {
        flag = false;
    }

    public static void reRun(Class<?> mainClass, String[] args) throws Exception {
        if (flag) {
            MainClassLoader reCL = new MainClassLoader();
            Class<?> clazz = reCL.loadClass(mainClass.getName());
            Class<?> reRunClass = reCL.loadClass(ReRun.class.getName());
            Method setFlag = reRunClass.getMethod("setFlag");
            setFlag.invoke(null);
            Method mainMethod = clazz.getMethod("main", String[].class);
            mainMethod.invoke(null, (Object) args);
            System.exit(0);
        }
    }

}
