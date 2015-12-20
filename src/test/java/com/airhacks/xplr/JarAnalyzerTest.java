package com.airhacks.xplr;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.jar.Manifest;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author airhacks.com
 */
public class JarAnalyzerTest {

    JarAnalyzer cut;
    private Path pathToJar;

    @Before
    public void init() {
        this.pathToJar = Paths.get("src/test/jars/afterburner.fx-1.6.2.jar");
    }

    @Test
    public void analyze() throws IOException {
        Manifest manifest = JarAnalyzer.getManifest(pathToJar);
        assertNotNull(manifest);
        POM mavenPOM = JarAnalyzer.getMavenPOM(pathToJar);
        assertNotNull(mavenPOM);
        System.out.println("mavenPOM = " + mavenPOM);
    }

    @Test
    public void getPackage() {
        String expected = "com.airhacks.afterburner.configuration";
        String actual = JarAnalyzer.getPackage(this.pathToJar);
        assertThat(actual, is(expected));
    }

}
