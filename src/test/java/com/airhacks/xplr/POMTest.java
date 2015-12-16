package com.airhacks.xplr;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author airhacks.com
 */
public class POMTest {

    @Test
    public void parsePOM() throws IOException {
        String content = new String(Files.readAllBytes(Paths.get("src/test/jars/testpom.xml")));
        POM pom = new POM(content);
        assertThat(pom.getArtifactId(), is("afterburner.fx"));
        assertThat(pom.getGroupId(), is("com.airhacks"));
        assertThat(pom.getPackaging(), is("jar"));
        assertThat(pom.getVersion(), is("1.6.2"));
    }

}
