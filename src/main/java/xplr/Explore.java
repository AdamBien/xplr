package xplr;

import com.airhacks.xplr.FileWalker;
import com.airhacks.xplr.JarAnalyzer;
import com.airhacks.xplr.JarFileInfo;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author airhacks.com
 */
public class Explore {

    public static void main(String[] args) {
        String start = args.length >= 1 ? args[0] : ".";
        String className = args.length >= 2 ? args[1] : null;
        System.out.println("xplr.Explorer " + start + " " + className);
        Path root = Paths.get(start);
        List<Path> jars = FileWalker.findJars(root);
        Stream<Path> stream = filter(className, jars.stream());
        Map<Path, List<JarFileInfo>> byPath = stream.map(JarAnalyzer::analyze).
                collect(Collectors.groupingBy(JarFileInfo::getFolderName));
        String report = byPath.entrySet().
                stream().
                map(e -> toString(e.getKey(), e.getValue())).
                collect(Collectors.joining("\n---\n", "\n", "\n----\n"));
        System.out.println("-------------");
        System.out.println(report);
        System.out.println("-------------");
    }

    static Stream<Path> filter(String className, Stream<Path> stream) {
        if (className != null) {
            return stream.filter(j -> JarAnalyzer.containsFileName(j, className));
        }
        return stream;
    }

    static String toString(Path path, List<JarFileInfo> jars) {
        String retVal = "#####################################\n";
        retVal += "Directory: " + path.toString() + "\n";
        retVal += "#####################################\n";
        retVal += jars.stream().map(j -> j.toString()).
                collect(Collectors.joining("\n---\n", "\n", "\n----\n"));
        return retVal;
    }
}
