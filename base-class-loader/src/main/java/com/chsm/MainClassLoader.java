package com.chsm;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

/**
 * @author chsmWork
 * @version v1.0
 * @since 2023/2/14 10:29
 */
public class MainClassLoader extends ClassLoader {

    public static boolean flag = true;

    public static void setFlag() {
        flag = false;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        try {
            byte[] classDate = getDate(name);

            //defineClass方法将字节码转化为类
            return defineClass(name, classDate, 0, classDate.length);

        } catch (Exception e) {

//            e.printStackTrace();
        }
        Set<String> keySet = CustomClassLoader.BUILD_MAP.keySet();
        for (String key : keySet) {
            if (name.equals(key)) {
                ClassLoader loader = new CustomClassLoader();
                return loader.loadClass(name);
            }
        }
        return super.loadClass(name);
    }

    //返回类的字节码
    private byte[] getDate(String className) throws ClassNotFoundException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(
                className.replace('.', File.separatorChar) + ".class");
        if (inputStream == null) {
            throw new ClassNotFoundException(className);
        }
        byte[] buffer;
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        int nextValue = 0;
        try {
            while ((nextValue = inputStream.read()) != -1) {
                byteStream.write(nextValue);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        buffer = byteStream.toByteArray();
        return buffer;
    }
}
