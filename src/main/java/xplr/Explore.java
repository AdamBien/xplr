package xplr;

import com.airhacks.xplr.FileWalker;
import com.airhacks.xplr.JarAnalyzer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author airhacks.com
 */
public class Explore {

    public static void main(String[] args) {
        String start = args.length <= 1 ? "." : args[0];
        String className = args.length <= 2 ? null : args[1];
        Path root = Paths.get(start);
        FileWalker fileWalker = new FileWalker();
        List<Path> jars = fileWalker.findJars(root);
        Stream<Path> stream = filter(className, jars.stream());
        String report = stream.map(JarAnalyzer::analyze).
                map(i -> i.toString()).
                collect(Collectors.joining("\n---\n", "\n", "\n----\n"));

        System.out.println("analyzedJars = " + report);
    }

    static Stream<Path> filter(String className, Stream<Path> stream) {
        if (className != null) {
            return stream.filter(j -> JarAnalyzer.containsFileName(j, className));
        }
        return stream;
    }
}
