import MyScanner.MyScanner;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WsppEvenCurrency {

    static void solve(String inputFile, String outputFile) {
        TreeMap<String, List<Integer>> wordCounter = new TreeMap<>(); // Для алфавитного порядка слов
        try (MyScanner scanner = new MyScanner(new FileReader(inputFile, StandardCharsets.UTF_8))) {
            int indexOfCurrentWord = 0;
            String word;
            while (scanner.hasNext()) {
                word = scanner.next();
                if (word == null) {
                    break;  // Если встретился конец файла
                }
                word = word.toLowerCase();
                indexOfCurrentWord++;
                wordCounter.computeIfAbsent(word, k -> new ArrayList<>()).add(indexOfCurrentWord);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Ошибка: файл не найден - " + e.getMessage());
            return;
        } catch (IOException e) {
            System.err.println("Ошибка ввода-вывода: " + e.getMessage());
            return;
        }

        // Преобразуем TreeMap в список для сортировки
        List<Map.Entry<String, List<Integer>>> sortedEntries = new ArrayList<>(wordCounter.entrySet());

        // Сортируем по количеству вхождений, при равенстве по первому индексу
        sortedEntries.sort((entry1, entry2) -> {
            int sizeComparison = Integer.compare(entry1.getValue().size(), entry2.getValue().size());
            if (sizeComparison != 0) {
                return sizeComparison;  // Сортируем по числу вхождений
            } else {
                return Integer.compare(entry1.getValue().get(0), entry2.getValue().get(0));  // По первому вхождению
            }
        });

        // Записываем результат в файл
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, StandardCharsets.UTF_8))) {
            for (Map.Entry<String, List<Integer>> entry : sortedEntries) {
                writer.write(entry.getKey() + " " + entry.getValue().size());
                int indOfElem = 0;
                int size_of_array = 0;
                for (int elem : entry.getValue()) {
                    if (elem % 2 == 0) {
                        size_of_array++;
                    }
                }
                if (size_of_array != 0) {
                    writer.write(" ");
                    for (int elem : entry.getValue()) {
                        if (elem % 2 == 0) {
                            indOfElem++;
                            writer.write(String.valueOf(elem));  // Преобразование числа в строку
                            if (indOfElem != size_of_array) {
                                writer.write(" ");
                            }
                        }
                    }
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

