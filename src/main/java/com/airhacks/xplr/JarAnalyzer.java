package com.airhacks.xplr;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;
import java.util.stream.Collectors;

/**
 *
 * @author airhacks.com
 */
public interface JarAnalyzer {

    public static Manifest getManifest(Path jar) {
        try (FileInputStream fileInputStream = new FileInputStream(jar.toFile())) {
            try (JarInputStream inputStream = new JarInputStream(fileInputStream)) {
                return inputStream.getManifest();
            }
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    public static boolean containsFileName(Path jar, String className) {
        try (FileInputStream fileInputStream = new FileInputStream(jar.toFile())) {
            try (JarInputStream inputStream = new JarInputStream(fileInputStream)) {
                JarEntry entry;
                while ((entry = inputStream.getNextJarEntry()) != null) {
                    if (entry.getName().equals(className)) {
                        return true;
                    }
                }
            }
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
        return false;
    }

    public static POM getMavenPOM(Path jar) {
        try (JarFile file = new JarFile(jar.toFile())) {
            POM pom = file.stream().peek(JarAnalyzer::log).
                    filter(e -> e.getName().endsWith("pom.xml")).
                    map((JarEntry e) -> {
                        try {
                            return file.getInputStream(e);
                        } catch (IOException ex) {
                            throw new IllegalStateException(ex);
                        }
                    }).
                    map(JarAnalyzer::read).
                    findFirst().
                    map(POM::new).
                    orElse(null);
            if (pom != null) {
                return pom;
            }
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
        return null;
    }

    public static JarFileInfo analyze(Path jar) {
        return new JarFileInfo(jar.toString(), getManifest(jar), getMavenPOM(jar));

    }

    static void log(JarEntry entry) {
        System.out.println(entry);
    }

    static String read(InputStream stream) {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(stream))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

}
