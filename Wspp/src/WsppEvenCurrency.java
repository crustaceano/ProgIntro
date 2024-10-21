import MyScanner.MyScanner;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WsppEvenCurrency {

    static void solve(String inputFile, String outputFile) {
        LinkedHashMap<String, List<Integer>> wordCounter = new LinkedHashMap<>();

        try (MyScanner scanner = new MyScanner(new FileReader(inputFile, StandardCharsets.UTF_8))) {
            StringBuilder currentLine = new StringBuilder(); // Вынесем переменную сюда
            int lineIndex = 1; // Индекс текущей строки

            while (scanner.hasNext()) {
                String word = scanner.next();
                if (word.equals("\n")) {
                    processLine(currentLine.toString(), wordCounter, lineIndex);
                    currentLine.setLength(0); // Сбрасываем строку
                    lineIndex++; // Переходим к следующей строке
                } else {
                    currentLine.append(word).append(" ");
                }
            }

            // Обрабатываем последнюю строку, если она не была обработана
            if (currentLine.length() > 0) {
                processLine(currentLine.toString(), wordCounter, lineIndex);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Ошибка: файл не найден - " + e.getMessage());
            return;
        } catch (IOException e) {
            System.err.println("Ошибка ввода-вывода: " + e.getMessage());
            return;
        }

        writeOutput(outputFile, wordCounter);
    }

    private static void processLine(String line, LinkedHashMap<String, List<Integer>> wordCounter, int lineIndex) {
        Map<String, Integer> countWordsByLine = new HashMap<>();
        StringBuilder cleanedWord = new StringBuilder();
        int wordPosition = 1; // Позиция слова в строке

        for (char c : line.toCharArray()) {
            if (Character.isLetter(c) || c == '\'' || c == '-' || c == '$') {
                cleanedWord.append(c); // Собираем слово
            } else if (Character.isWhitespace(c)) {
                if (cleanedWord.length() > 0) {
                    String word = cleanedWord.toString().toLowerCase();
                    countWordsByLine.put(word, countWordsByLine.getOrDefault(word, 0) + 1);

                    // Добавляем четные вхождения
                    if (countWordsByLine.get(word) % 2 == 0) {
                        addWord(word, wordCounter, lineIndex, wordPosition);
                    }
                    cleanedWord.setLength(0); // Сбрасываем текущее слово
                    wordPosition++; // Переходим к следующему слову
                }
            }
        }
        // Проверяем последнее слово в строке
        if (cleanedWord.length() > 0) {
            String word = cleanedWord.toString().toLowerCase();
            countWordsByLine.put(word, countWordsByLine.getOrDefault(word, 0) + 1);

            if (countWordsByLine.get(word) % 2 == 0) {
                addWord(word, wordCounter, lineIndex, wordPosition);
            }
        }
    }

    private static void addWord(String word, LinkedHashMap<String, List<Integer>> wordCounter, int lineIndex, int wordPosition) {
        // Добавляем слово в словарь с указанием номера строки и позиции слова в строке
        wordCounter.computeIfAbsent(word, k -> new ArrayList<>()).add(lineIndex);
        wordCounter.get(word).add(wordPosition);
    }

    private static void writeOutput(String outputFile, LinkedHashMap<String, List<Integer>> wordCounter) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, StandardCharsets.UTF_8))) {
            for (Map.Entry<String, List<Integer>> entry : wordCounter.entrySet()) {
                List<Integer> occurrences = entry.getValue();
                writer.write(entry.getKey());

                // Записываем индексы строк и позиции слов для четных вхождений
                for (int i = 0; i < occurrences.size(); i += 2) {
                    writer.write(" " + occurrences.get(i) + " " + occurrences.get(i + 1));
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Ошибка ввода-вывода: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Количество переданных аргументов неверно, передайте два аргумента <input_file> <output_file>");
            return;
        }
        solve(args[0], args[1]);
    }
}
