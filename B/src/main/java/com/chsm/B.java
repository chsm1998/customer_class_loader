package com.chsm;

/**
 * @author chsmWork
 * @version v1.0
 * @since 2023/2/14 8:16
 */
public class B {

    public static void main(String[] args) {
        new B().out();
    }

    public void out() {
        C c = new C();
        System.out.println(C.class.getClassLoader());
        c.helloV2();
    }

}
