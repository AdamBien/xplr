package com.airhacks.xplr;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author airhacks.com
 */
public class FileWalkerTest {

    private Path pathToJar;
    private FileWalker cut;

    @Before
    public void init() {
        this.pathToJar = Paths.get("src/test/");
    }

    @Test
    public void isJar() {
        assertTrue(FileWalker.isJar(Paths.get("hugo.jar")));
        assertFalse(FileWalker.isJar(Paths.get("hugo/")));
    }

    @Test
    public void isAbsolutePathWithPlainDottedFile() {
        Path file = Paths.get("org.eclipse.persistence.jpa.jar");
        Path expected = Paths.get("./org.eclipse.persistence.jpa.jar");
        Path actual = FileWalker.asAbsolutePath(file);
        assertThat(actual.toString(), is(expected.toString()));
    }

    @Test
    public void isAbsolutePathWithPlainFile() {
        Path file = Paths.get("hugo.jar");
        Path expected = Paths.get("./hugo.jar");
        Path actual = FileWalker.asAbsolutePath(file);
        assertThat(actual.toString(), is(expected.toString()));
    }

    @Test
    public void isAbsolutePathWithFileWithPath() {
        Path file = Paths.get("/temp/hugo.jar");
        Path expected = Paths.get("/temp/hugo.jar");
        Path actual = FileWalker.asAbsolutePath(file);
        assertThat(actual.toString(), is(expected.toString()));
    }


    @Test
    public void findJars() {
        Path expected = Paths.get("src/test/jars/afterburner.fx-1.6.2.jar");
        List<Path> jars = FileWalker.findJars(this.pathToJar);
        assertThat(jars.size(), is(1));
        assertThat(jars.get(0), is(expected));
    }

}
