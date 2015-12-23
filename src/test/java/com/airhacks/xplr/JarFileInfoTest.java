package com.airhacks.xplr;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author airhacks.com
 */
public class JarFileInfoTest {

    @Test
    public void limitShortString() {
        String expected = "expected";
        String actual = JarFileInfo.limit(expected);
        assertThat(actual, is(expected));
    }

    @Test
    public void limitTooLong() {
        String tooLong = "";
        for (int i = 0; i < 256; i++) {
            tooLong += i;
        }
        String actual = JarFileInfo.limit(tooLong);
        assertThat(actual.length(), is(JarFileInfo.MAX_LENGTH + 3 /* the three dots*/));

    }

}
