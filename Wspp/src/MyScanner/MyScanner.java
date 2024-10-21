package MyScanner;

import java.io.*;
import java.util.function.Predicate;

public class MyScanner implements AutoCloseable {
    private BufferedReader reader;
    private String buffer;
    private int position;
    private Predicate<Character> delimiterChecker; // Функция для проверки разделителей

    // Конструктор по умолчанию
    public MyScanner(Reader reader) {
        this(reader, getDefaultDelimiterChecker()); // Используем функцию по умолчанию
    }

    // Конструктор с пользовательской функцией для проверки разделителей
    public MyScanner(Reader reader, Predicate<Character> delimiterChecker) {
        this.reader = new BufferedReader(reader);
        this.buffer = "";
        this.position = 0;
        this.delimiterChecker = delimiterChecker;
    }

    // Метод для обновления буфера
    private void fillBuffer() throws IOException {
        char[] tempBuffer = new char[1024];
        int bytesRead = reader.read(tempBuffer);
        if (bytesRead != -1) {
            buffer = new String(tempBuffer, 0, bytesRead);
            position = 0; // Сброс позиции для нового блока данных
        } else {
            buffer = null; // Конец файла
        }
    }

    public String next() throws IOException {
        StringBuilder word = new StringBuilder();

        while (true) {
            if (buffer == null || position >= buffer.length()) {
                fillBuffer();
                if (buffer == null) {
                    return word.length() > 0 ? word.toString() : null;
                }
                position = 0;
            }

            char c = buffer.charAt(position++);
            if (delimiterChecker.test(c)) {
                if (word.length() > 0) {
                    return word.toString();
                }
            } else {
                word.append(c);
            }
        }
    }

    public boolean hasNext() throws IOException {
        while (true) {
            if (buffer == null || position >= buffer.length()) {
                fillBuffer();
                if (buffer == null) {
                    return false;
                }
                position = 0;
            }

            char c = buffer.charAt(position);
            if (!delimiterChecker.test(c)) {
                return true;
            } else {
                position++;
            }
        }
    }

    // Метод для чтения целой строки
    public String nextLine() throws IOException {
        StringBuilder line = new StringBuilder();
        while (hasNextLine()) {
            char c = (char) reader.read();
            if (c == '\n') {
                break; // Конец строки
            }
            line.append(c);
        }
        return line.length() > 0 ? line.toString() : null;
    }

    // Метод для проверки наличия следующей строки
    public boolean hasNextLine() throws IOException {
        reader.mark(1); // Ставим метку на текущей позиции
        int nextChar = reader.read(); // Читаем следующий символ
        if (nextChar == -1) {
            return false; // Конец файла
        }
        reader.reset(); // Возвращаемся к метке
        return true; // Есть следующая строка
    }

    public void close() throws IOException {
        reader.close();
    }

    // Метод для получения функции по умолчанию
    private static Predicate<Character> getDefaultDelimiterChecker() {
        return c -> !(Character.isLetter(c) || c == '\'' || Character.getType(c) == Character.DASH_PUNCTUATION);
    }
}
