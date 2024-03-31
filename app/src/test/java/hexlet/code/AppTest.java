package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.io.File;

public final class AppTest {

    @Test
    public void testGenerateDiffBetweenFlatJsonFiles() throws Exception {
        // Пути к тестовым файлам-фикстурам
        File file1 = new File("src/test/resources/file1.json");
        File file2 = new File("src/test/resources/file2.json");

        // Создаем экземпляр класса Differ
        Differ differ = new Differ();

        // Вызываем метод generate с тестовыми файлами
        String actualDiff = differ.generate(file1, file2);

        // Ожидаемый результат (примерный формат, вам нужно будет подставить фактический ожидаемый результат)
        String expectedDiff = "{\n"
                + "  - follow: false\n"
                + "  - proxy: 123.234.53.22\n"
                + "    host: hexlet.io\n"
                + "  - timeout: 50\n"
                + "  + timeout: 20\n"
                + "  + verbose: true\n"
                + "}";

        // Проверяем, что фактический результат работы метода соответствует ожидаемому
        assertEquals(expectedDiff, actualDiff);
    }

    // Дополнительные тесты...
}
