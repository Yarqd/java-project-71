//package hexlet.code;
//
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import java.io.File;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//public class AppTest {
//
//    private static final Path RESOURCES_PATH = Paths.get("src", "main", "resources");
//
//    @Test
//    public void testCompareJsonFiles() throws Exception {
//        File file1 = RESOURCES_PATH.resolve("file1.json").toFile();
//        File file2 = RESOURCES_PATH.resolve("file2.json").toFile();
//
//        String actualDiff = Differ.generate(file1, file2, "stylish");
//
//        String expectedDiff = "{"
//                + "\n  - follow: false"
//                + "\n    host: hexlet.io"
//                + "\n  - proxy: 123.234.53.22"
//                + "\n  - timeout: 50"
//                + "\n  + timeout: 20"
//                + "\n  + verbose: true"
//                + "\n}\n";
//        assertEquals(expectedDiff, actualDiff);
//    }
//
//    @Test
//    public void testCompareYmlFiles() throws Exception {
//        File file1 = RESOURCES_PATH.resolve("file1.yml").toFile();
//        File file2 = RESOURCES_PATH.resolve("file2.yml").toFile();
//
//        String actualDiff = Differ.generate(file1, file2, "stylish");
//
//        String expectedDiff = "{"
//                + "\n  - follow: false"
//                + "\n    host: hexlet.io"
//                + "\n  - proxy: 123.234.53.22"
//                + "\n  - timeout: 50"
//                + "\n  + timeout: 20"
//                + "\n  + verbose: true"
//                + "\n}\n";
//        assertEquals(expectedDiff, actualDiff);
//    }

//    @Test
//    public void testCompareJsonAttachedFiles() throws Exception {
//        File file1 = RESOURCES_PATH.resolve("filepath1.json").toFile();
//        File file2 = RESOURCES_PATH.resolve("filepath2.json").toFile();
//
//        String actualDiff = Differ.generate(file1, file2, "stylish");
//
//        String expectedDiff = "{\n" +
//                "    chars1: [a, b, c]\n" +
//                "  - chars2: [d, e, f]\n" +
//                "  + chars2: false\n" +
//                "  - checked: false\n" +
//                "  + checked: true\n" +
//                "  - default: null\n" +
//                "  + default: [value1, value2]\n" +
//                "  - id: 45\n" +
//                "  + id: null\n" +
//                "  - key1: value1\n" +
//                "  + key2: value2\n" +
//                "    numbers1: [1, 2, 3, 4]\n" +
//                "  - numbers2: [2, 3, 4, 5]\n" +
//                "  + numbers2: [22, 33, 44, 55]\n" +
//                "  - numbers3: [3, 4, 5]\n" +
//                "  + numbers4: [4, 5, 6]\n" +
//                "  + obj1: {nestedKey=value, isNested=true}\n" +
//                "  - setting1: Some value\n" +
//                "  + setting1: Another value\n" +
//                "  - setting2: 200\n" +
//                "  + setting2: 300\n" +
//                "  - setting3: true\n" +
//                "  + setting3: none\n" +
//                "}\n";
//        assertEquals(expectedDiff, actualDiff);
//    }
//}
