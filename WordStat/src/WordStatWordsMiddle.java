import MyScanner.MyScanner;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WordStatWordsMiddle {

    static void solve(String inputFile, String outputFile) {
        TreeMap<String, Integer> wordCounter = new TreeMap<>(Comparator.reverseOrder());
        try (MyScanner scanner = new MyScanner(new FileReader(inputFile, StandardCharsets.UTF_8))) {
            String word;
            while (scanner.hasNext()) {
                word = scanner.next().toLowerCase(); // Приводим слово к нижнему регистру
                String transformedWord = transformedWord(word);
                wordCounter.put(transformedWord, wordCounter.getOrDefault(transformedWord, 0) + 1);

            }
        } catch (FileNotFoundException e) {
            System.err.println("Ошибка: файл не найден - " + e.getMessage());
            return;
        } catch (IOException e) {
            System.err.println("Ошибка ввода-вывода: " + e.getMessage());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, StandardCharsets.UTF_8))) {
            // Вывод отсортированных данных в файл
            for (Map.Entry<String, Integer> entry : wordCounter.entrySet()) {
                writer.write(entry.getKey() + " " + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Ошибка ввода-вывода: " + e.getMessage());
        }
    }

    static String transformedWord(String word) {
        if (word.length() >= 7) {
            return word.substring(3, word.length() - 3); // Убираем первые и последние 3 символа
        }
        return word; // Возвращаем слово, если его длина меньше 7
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Количество переданных аргументов неверно, передайте два аргумента <input_file> <output_file>");
            return;
        }
        solve(args[0], args[1]);
    }
}
