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
    private final String longestPackagePath;
    final static int MAX_LENGTH = 128;

    public JarFileInfo(Path fileName, Manifest manifest, POM pom, String longestPackagePath) {
        this.fileName = fileName;
        this.manifest = manifest;
        this.pom = pom;
        this.longestPackagePath = longestPackagePath;
    }

    public String getManifest() {
        Attributes mainAttributes = this.manifest.getMainAttributes();
        String main = mainAttributes.entrySet().stream().
                map(e -> e.getKey() + ":" + limit(e.getValue())).
                collect(Collectors.joining("\n"));
        return main;
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

    public String getLongestPackagePath() {
        return longestPackagePath;
    }

    public String getMavenInstallCommand() {
        return "mvn install:install-file -Dfile=" + this.fileName
                + " -DgroupId=" + getGroupId()
                + " -DartifactId=" + getArtifactId()
                + " -Dversion=" + getVersion()
                + " -Dpackaging=" + getPackaging();
    }

    String getGroupId() {
        String groupId = this.pom == null ? null : this.pom.getGroupId();
        if (groupId == null) {
            groupId = getLongestPackagePath();
        }
        return groupId;
    }

    String getArtifactId() {
        String artifactId = this.pom == null ? null : this.pom.getArtifactId();
        if (artifactId == null) {
            artifactId = getFileNameWithoutExtension();
        }
        return artifactId;
    }

    String extractFileNameWithoutExtension(Path path) {
        Path file = path.getFileName();
        String nameAsString = file.toString();
        int indexOfDot = nameAsString.lastIndexOf(".");
        return nameAsString.substring(0, indexOfDot);
    }

    String getFileNameWithoutExtension() {
        return this.extractFileNameWithoutExtension(this.getFileName());
    }

    String getVersion() {
        String version = this.pom == null ? null : this.pom.getVersion();
        if (version == null) {
            return "1.0";
        } else {
            return version;
        }
    }

    String getPackaging() {
        String packaging = this.pom == null ? null : this.pom.getPackaging();
        if (packaging == null) {
            return "jar";
        } else {
            return packaging;
        }
    }

    @Override
    public String toString() {
        String msg = "# ";
        msg += "Jar: " + getFileName() + "\n";
        msg += "## Manifest: " + "\n";
        msg += "## Package: " + getLongestPackagePath() + "\n";
        msg += getManifest() + "\n";
        if (pom != null) {
            msg += "\n## POM: " + "\n";
            msg += getPom() + "\n";
        } else {
            POM suggestion = new POM(this.getGroupId(), this.getArtifactId(), this.getVersion());
            msg += "\n## Suggestion: " + "\n";
            msg += suggestion + "\n";
        }
        msg += "## MVN install command: " + "\n";
        msg += getMavenInstallCommand();
        return msg;
    }

    static String limit(Object value) {
        String stringified = value.toString();
        int length = stringified.length();
        if (length > MAX_LENGTH) {
            return stringified.substring(0, MAX_LENGTH) + "...";
        }
        return stringified;
    }

}
