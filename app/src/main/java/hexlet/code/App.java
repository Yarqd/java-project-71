package hexlet.code;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine;

import java.io.File;
import java.util.concurrent.Callable;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0",
        description = "Compares two configuration files and shows a difference.")
class App implements Callable<Integer> {

    @Parameters(index = "0", description = "path to first file")
    private File firstFile;

    @Parameters(index = "1", description = "path to second file")
    private File secondFile;

    @Option(names = {"-f", "--format"}, description = "output format [default: stylish]")
    private String format = "stylish";

    @Override
    public Integer call() throws Exception {
        // Здесь должна быть ваша логика сравнения двух файлов и вывод результата в выбранном формате.
        // Поскольку форматы реализованы не будут, мы просто печатаем пути к файлам в качестве заглушки.
        System.out.printf("Comparing:n- %sn- %snFormat: %sn", firstFile, secondFile, format);
        // Здесь должен быть вызов функции, которая сделает реальное сравнение и вернет результат.
        // Предполагаем, что функция вернет 0 при успешном сравнении.
        return 0;
    }

    public static void main(String... args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}