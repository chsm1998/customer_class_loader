package chsm;

import com.chsm.A;
import com.chsm.B;
import com.chsm.ReRun;

/**
 * @author chsmWork
 * @version v1.0
 * @since 2023/2/14 10:34
 */
public class SuperTest {

    public static void main(String[] args) throws Exception {
        ReRun.reRun(SuperTest.class, args);
        new A().out();
        new B().out();
    }

}
