package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import java.util.concurrent.Callable;
import hexlet.code.formatters.StylishFormatter;
import hexlet.code.formatters.PlainFormatter;
import hexlet.code.formatters.Formatter;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0",
        description = "Compares two configuration files and shows a difference.")
public final class App implements Callable<Integer> {

    @Parameters(index = "0", description = "path to first file")
    private String firstFilePath;

    @Parameters(index = "1", description = "path to second file")
    private String secondFilePath;

    @Option(names = {"-f", "--format"}, description = "output format [default: ${DEFAULT-VALUE}]")
    private String format = "stylish";

    @Override
    public Integer call() throws Exception {
        Formatter formatter;
        if ("plain".equals(format)) {
            formatter = new PlainFormatter();
        } else {
            formatter = new StylishFormatter();
        }

        String diff = Differ.generate(firstFilePath, secondFilePath, formatter);
        System.out.println(diff);
        return 0;
    }

    public static void main(String... args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
