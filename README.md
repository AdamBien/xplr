# xplr

Java 8 Command Line Jar Explorer

xplr traverses recursively JARs, extracts the versioning information from ```manifest.mf``` and ```pom.xml``` and generates
```mvn install:install-file```. xplr is helpful for extracting dependency information for Maven dependencies from "legacy" JARs.

```
#####################################
Directory: .
#####################################

# Jar: ./original-xplr.jar
## Manifest: 
## Package: com.airhacks.xplr
Archiver-Version:Plexus Archiver
Built-By:abien
Created-By:Apache Maven 3.3.3
Build-Jdk:1.8.0_77
Manifest-Version:1.0

## POM: 
<dependency>
 <groupId>com.airhacks</groupId>
 <artifactId>xplr</artifactId>
 <version>0.0.1</version>
 <packaging>jar</packaging>
</dependency>
## MVN install command: 
mvn install:install-file -Dfile=./original-xplr.jar -DgroupId=com.airhacks -DartifactId=xplr -Dversion=0.0.1 -Dpackaging=jar
```


## Requirements

Java 8

## Usage

```java -jar xplr.jar [FOLDER] [CLASS NAME]```

FOLDER (optional): specifies the folder, default is the current working directory

CLASS NAME (optional): searches for jars containing the specified class name. The search is case insensitive. 

