package MyScanner;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class MyScanner implements AutoCloseable {
    private BufferedReader reader;
    private String buffer;
    private int position;
    private Set<Character> customWordChars; // Множество для символов, которые считаются частью слова
    private int buffer_size = 1024;
    // Конструктор по умолчанию
    public MyScanner(Reader reader) {
        this(reader, new HashSet<>()); // Используем конструктор с символами по умолчанию
    }
    // Конструктор с дополнительными символами, которые считаются частью слова
    public MyScanner(Reader reader, Set<Character> customWordChars) {
        this.reader = new BufferedReader(reader);
        this.buffer = "";
        this.position = 0;
        this.customWordChars = customWordChars;
    }
    public void setBufferSize(int buffer_size){
        this.buffer_size = buffer_size;
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
            if (isDelimiter(c)) {
                if (word.length() > 0) {
                    return word.toString();
                }
            } else {
                word.append(c);
            }
        }
    }

    private boolean isDelimiter(char c) {
        return !(Character.isLetter(c) || c == '\'' || isDash(c) || customWordChars.contains(c));
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
                position = 0;
            }

            char c = buffer.charAt(position);
            if (!isDelimiter(c)) {
                return true;
            } else {
                position++;
            }
        }
    }

    public void close() throws IOException {
        reader.close();
    }
}
