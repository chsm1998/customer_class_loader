package chsm;

import com.chsm.A;
import com.chsm.B;

/**
 * @author chsmWork
 * @version v1.0
 * @since 2023/2/14 10:52
 */
public class NoClassLoaderTest {

    public static void main(String[] args) {
        new A().out();
        new B().out();
    }

}
