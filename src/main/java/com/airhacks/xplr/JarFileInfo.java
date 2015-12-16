package com.airhacks.xplr;

import java.nio.file.Path;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.stream.Collectors;

/**
 *
 * @author airhacks.com
 */
public class JarFileInfo {

    private final Manifest manifest;
    private final Path fileName;
    private final POM pom;

    public JarFileInfo(Path fileName, Manifest manifest, POM pom) {
        this.fileName = fileName;
        this.manifest = manifest;
        this.pom = pom;
    }

    public String getManifest() {
        String entries = this.manifest.
                getEntries().
                entrySet().
                stream().
                map(e -> e.getKey() + ":" + e.getValue()).
                collect(Collectors.joining("/n"));
        Attributes mainAttributes = this.manifest.getMainAttributes();
        String main = mainAttributes.entrySet().stream().
                map(e -> e.getKey() + ":" + e.getValue()).
                collect(Collectors.joining("\n"));
        return entries + "\n" + main;
    }

    public Path getFolderName() {
        return this.fileName.getParent();
    }

    public Path getFileName() {
        return fileName;
    }

    public POM getPom() {
        return pom;
    }

    @Override
    public String toString() {
        String msg = "# ";
        msg += "Jar: " + getFileName() + "\n";
        msg += "## Manifest: " + "\n";
        msg += getManifest() + "\n";
        if (pom != null) {
            msg += "\n## POM: " + "\n";
            msg += getPom();
        } else {
            msg += "-no pom information-";
        }
        return msg;
    }

}
