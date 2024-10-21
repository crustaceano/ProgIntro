package MyScanner;

import java.io.*;

public class MyScanner implements AutoCloseable {
    private BufferedReader reader;
    private String buffer;
    private int position;
    private int buffer_size = 1024;
    private String allowedChars = "a-zA-Z";

    // Конструктор по умолчанию
    public MyScanner(InputStream inputStream, int buffer_size){
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.buffer = "";
        this.position = 0;
        this.buffer_size = buffer_size;
    }
    public MyScanner(Reader inputReader) {
        this.reader = new BufferedReader(inputReader);
        this.buffer = "";
        this.position = 0;
    }
    public MyScanner(InputStream inputStream) {
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.buffer = "";
        this.position = 0;
    }
    public MyScanner(Reader inputReader, String allowedChars) {
        this.reader = new BufferedReader(inputReader);
        this.buffer = "";
        this.position = 0;
        this.allowedChars = allowedChars;  // Пользователь может указать допустимые символы
    }
    private void fillBuffer() throws IOException {
        char[] tempBuffer = new char[buffer_size];
        int bytesRead = reader.read(tempBuffer);
        if (bytesRead != -1) {
            buffer = new String(tempBuffer, 0, bytesRead);
            position = 0;
        } else {
            buffer = null;
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
            }

            char c = buffer.charAt(position++);
            if (c == '\n') {
                if (word.length() > 0) {
                    position--; // Вернемся назад, чтобы \n был обработан на следующем вызове
                    return word.toString();
                } else {
                    return "\n";
                }
            } else if (Character.isWhitespace(c)) {
                if (word.length() > 0) {
                    return word.toString();
                }
            } else if (String.valueOf(c).matches("[" + allowedChars + "]")) {  // Если символ допустим
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
            }

            char c = buffer.charAt(position);
            if (!Character.isWhitespace(c) || c == '\n') {
                return true;
            } else {
                position++;
            }
        }
    }
    public int nextInt() throws IOException {
        StringBuilder number = new StringBuilder();

        while (true) {
            if (buffer == null || position >= buffer.length()) {
                fillBuffer();
                if (buffer == null) {
                    throw new IOException("End of input");
                }
            }

            char c = buffer.charAt(position++);
            if (Character.isDigit(c) || (c == '-' && number.length() == 0)) {
                number.append(c);
            } else if (number.length() > 0) {
                return Integer.parseInt(number.toString());
            }
        }
    }

    public String nextLine() throws IOException {
        StringBuilder line = new StringBuilder();

        while (true) {
            if (buffer == null || position >= buffer.length()) {
                fillBuffer();
                if (buffer == null) {
                    return line.length() > 0 ? line.toString() : null;
                }
            }

            char c = buffer.charAt(position++);
            if (c == '\n') {
                return line.toString();
            } else if (c != '\r') {
                line.append(c); // Добавляем символ к строке, игнорируя \r
            }
        }
    }
    public boolean hasNextLine() throws IOException {
        while (true) {
            if (buffer == null || position >= buffer.length()) {
                fillBuffer();
                if (buffer == null) {
                    return false;
                }
            }

            char c = buffer.charAt(position);
            if (c == '\n') {
                position++; // Пропускаем пустую строку
                return true;
            } else {
                return true; // Не пустая строка
            }
        }
    }
    public void close() throws IOException {
        reader.close();
    }
}
