package xplr;

import org.junit.Test;

/**
 *
 * @author airhacks.com
 */
public class ExplorerTest {


    @Test(expected = IllegalStateException.class)
    public void exploreNotExistingJar() {
        String[] arguments = new String[]{"doesnotexists.txt"};
        Explorer.main(arguments);
    }

    @Test
    public void exploreExistingJar() {
        String[] arguments = new String[]{"src/test/jars/afterburner.fx-1.6.2.jar"};
        Explorer.main(arguments);
    }

}
