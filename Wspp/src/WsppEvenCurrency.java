import MyScanner.MyScanner;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WsppEvenCurrency {

    static void solve(String inputFile, String outputFile) {
        LinkedHashMap<String, List<Integer>> wordCounter = new LinkedHashMap<>(); // Четные вхождения
        LinkedHashMap<String, Integer> totalWordCount = new LinkedHashMap<>(); // Общее количество вхождений

        try (MyScanner scanner = new MyScanner(new FileReader(inputFile, StandardCharsets.UTF_8),
                c -> !(Character.isLetter(c) || c == '\'' || Character.getType(c) == Character.DASH_PUNCTUATION || Character.getType(c) == Character.getType('\n') || Character.getType(c) == Character.CURRENCY_SYMBOL))) {
            StringBuilder currentLine = new StringBuilder();
            int word_ind = 1;

            while (scanner.hasNext()) {
                String word = scanner.next();
                if (word.contains("\n")) {
                    processLine(currentLine.toString(), wordCounter, totalWordCount, word_ind);
                    currentLine.setLength(0);  // Очистка для новой строки
                } else {
                    currentLine.append(word).append(" ");
                }
            }
            if (!currentLine.isEmpty()) {
                processLine(currentLine.toString(), wordCounter, totalWordCount, word_ind);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Ошибка: файл не найден - " + e.getMessage());
            return;
        } catch (IOException e) {
            System.err.println("Ошибка ввода-вывода: " + e.getMessage());
            return;
        }

        writeOutput(outputFile, wordCounter, totalWordCount);
    }

    private static void processLine(String line, LinkedHashMap<String, List<Integer>> wordCounter,
                                    Map<String, Integer> totalWordCount, int word_ind) {
        Map<String, Integer> countWordsByLine = new HashMap<>();
        StringBuilder cleanedWord = new StringBuilder();
        int wordPosition = 1;

        for (char c : line.toCharArray()) {
            // Debug: Print each character processed
//            System.out.println("Processing character: " + c + " (type: " + Character.getType(c) + ")");

            if (!Character.isWhitespace(c)) {
                cleanedWord.append(c);
            } else {
                if (!cleanedWord.isEmpty()) {
                    String word = cleanedWord.toString().toLowerCase();
                    countWordsByLine.put(word, countWordsByLine.getOrDefault(word, 0) + 1);
                    totalWordCount.put(word, totalWordCount.getOrDefault(word, 0) + 1);
                    if (countWordsByLine.get(word) % 2 == 0) {
                        addWord(word, wordCounter, word_ind);
                    }
                    cleanedWord.setLength(0);
                    wordPosition++;
                    word_ind++;
                }
            }
        }

        // Check for any remaining word at the end of the line
        if (cleanedWord.length() > 0) {
            String word = cleanedWord.toString().toLowerCase();
            countWordsByLine.put(word, countWordsByLine.getOrDefault(word, 0) + 1);
            totalWordCount.put(word, totalWordCount.getOrDefault(word, 0) + 1);
            if (countWordsByLine.get(word) % 2 == 0) {
                addWord(word, wordCounter, word_ind);
            }
            word_ind++;
        }
    }


    private static void addWord(String word, LinkedHashMap<String, List<Integer>> wordCounter, int wordPosition) {
        wordCounter.computeIfAbsent(word, k -> new ArrayList<>()).add(wordPosition);
//        System.err.println(word + " " + wordCounter.get(word).size());
    }

    private static void writeOutput(String outputFile, LinkedHashMap<String, List<Integer>> wordCounter,
                                    Map<String, Integer> totalWordCount) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, StandardCharsets.UTF_8))) {
            for (String word: totalWordCount.keySet()) {
                // Записываем слово и общее количество всех его вхождений
                writer.write(word + " " + totalWordCount.get(word));

                // Записываем четные вхождения (индексы строк и позиции слов)
                for(Integer elem: wordCounter.getOrDefault(word, new ArrayList<>())){
                    writer.write(" " + elem);
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
