package com.chsm;

/**
 * @author chsmWork
 * @version v1.0
 * @since 2023/2/14 8:14
 */
public class A {

    public static void main(String[] args) {
        new A().out();
    }

    public void out() {
        System.out.println(C.class.getClassLoader());
        new C().hello();
    }

}
