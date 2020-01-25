package com.airhacks.xplr;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author airhacks.com
 */
public interface FileWalker {

    public static List<Path> findJars(Path root) {
        if (isJar(root)) {
            Path fileWithAbsolutePath = asAbsolutePath(root);
            return Arrays.asList(fileWithAbsolutePath);
        }
        return scanFolder(root);
    }

    static Path asAbsolutePath(Path file) {
        String asString = file.toString();
        if (!asString.startsWith(".") && !asString.startsWith(File.pathSeparator)) {
            return Paths.get(".").resolve(file);
        }
        return file;
    }

    static boolean isJar(Path file) {
        return file.getFileName().toString().endsWith(".jar");
    }

    public static List<Path> scanFolder(Path root) throws IllegalStateException {
        List<Path> jars = new ArrayList<>();
        SimpleFileVisitor visitor = new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
                if (!attributes.isDirectory()) {
                    if (file.toString().endsWith(".jar")) {
                        jars.add(file);
                    }
                }
                return FileVisitResult.CONTINUE;
            }
        };
        try {
            Files.walkFileTree(root, visitor);
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
        return jars;
    }

}
