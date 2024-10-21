package MyScanner;

import java.io.*;

public class MyScanner implements AutoCloseable {
    private BufferedReader reader;
    private String buffer;
    private int position;

    // Конструктор по умолчанию
    public MyScanner(Reader reader) {
        this.reader = new BufferedReader(reader);
        this.buffer = "";
        this.position = 0;
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
                position = 0; // Сбрасываем позицию после заполнения буфера
            }

            char c = buffer.charAt(position++);
            if (isDelimiter(c)) {
                if (word.length() > 0) {
                    return word.toString(); // Возвращаем найденное слово
                }
            } else {
                word.append(c); // Добавляем символ к слову
            }
        }
    }

    private boolean isDelimiter(char c) {
        return !(Character.isLetter(c) || c == '\'' || isDash(c));
    }

    private boolean isDash(char c) {
        return Character.getType(c) == Character.DASH_PUNCTUATION;
    }

    public boolean hasNext() throws IOException {
        while (true) {
            if (buffer == null || position >= buffer.length()) {
                fillBuffer();
                if (buffer == null) {
                    return false;
                }
                position = 0; // Сбрасываем позицию после заполнения буфера
            }

            char c = buffer.charAt(position);
            if (!isDelimiter(c)) {
                return true;
            } else {
                position++; // Пропускаем разделители
            }
        }
    }

    public void close() throws IOException {
        reader.close();
    }
}
