package com.chsm;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import sun.tools.jar.resources.jar;

import java.io.*;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * @author chsmWork
 * @version v1.0
 * @since 2022/1/22 8:30
 */
public class CustomClassLoader extends ClassLoader {

    public static Map<String, Map<String, String>> BUILD_MAP;

    private static List<String> userLib;

    static {
        InputStream resourceAsStream = null;
        try {
            resourceAsStream = CustomClassLoader.class.getResourceAsStream("/build.json");
        } catch (Exception ignore) {

        }
        if (resourceAsStream != null) {
            BUILD_MAP = new Gson().fromJson(new InputStreamReader(resourceAsStream), new TypeToken<Map<String, Map<String, String>>>() {
            }.getType());
        }
        if (BUILD_MAP == null) {
            BUILD_MAP = Collections.emptyMap();
        }
        String libPathStr = System.getProperty("java.class.path");
        String[] libPathList = libPathStr.split(";");
        userLib = Arrays.stream(libPathList)
                .filter(v -> !v.contains("jre"))
                .filter(v -> v.endsWith(".jar"))
                .filter(v -> !v.contains("IntelliJ IDEA"))
                .filter(v -> !v.contains("maven_jar"))
                .collect(Collectors.toList());
    }

    ;

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        Class<?> aClass = null;
        if (name.startsWith("com.chsm")) {
            try {
                aClass = findClass(name);
            } catch (Exception ignore) {

            }
        }
        if (aClass == null) {
            aClass = super.loadClass(name);
        }
        return aClass;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] b = new byte[0];
        try {
            b = getDate(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defineClass(name, b, 0, b.length);
    }

    private byte[] loadClassFromFile(String fileName) throws ClassNotFoundException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(
                fileName.replace('.', File.separatorChar) + ".class");
        if (inputStream == null) {
            throw new ClassNotFoundException(fileName);
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

    //返回类的字节码
    private byte[] getDate(String className) throws IOException, ClassNotFoundException {
        String tmp = className.replaceAll("\\.", "/");
        InputStream is = null;
        String customerVersion = isCustomerVersion(className);
        for (String jarPath : userLib) {
            File jar = new File(jarPath);
            if (customerVersion != null && !jar.getAbsolutePath().contains(customerVersion)) {
                continue;
            }
            JarFile jarFile = new JarFile(jar.getAbsolutePath());
            JarEntry entry = jarFile.getJarEntry(tmp + ".class");
            if (entry == null) {
                continue;
            }
            is = jarFile.getInputStream(entry);
            break;
        }
        if (is == null) {
            throw new ClassNotFoundException();
        }
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        int nextValue = is.read();
        while (-1 != nextValue) {
            byteStream.write(nextValue);
            nextValue = is.read();
        }

        return byteStream.toByteArray();
    }

    /**
     * 获取当前加载版本
     *
     * @param className 类名称
     * @return 当前加载版本
     */
    private String isCustomerVersion(String className) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            Map<String, String> map = BUILD_MAP.get(stackTraceElement.getClassName());
            if (map != null) {
                String version = map.get(className);
                if (version != null) {
                    return version;
                }
            }
        }
        return null;
    }
}
