import MyScanner.MyScanner;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Wspp {

    static void solve(String inputFile, String outputFile) {
        // Используем LinkedHashMap для сохранения порядка вставки
        Map<String, List<Integer>> wordCounter = new HashMap<>();
        List<String> words = new ArrayList<>();
        try (MyScanner scanner = new MyScanner(new FileReader(inputFile, StandardCharsets.UTF_8))) {
            int indexOfCurrentWord = 0;
            String word;
            while (scanner.hasNext()) {
                word = scanner.next().toLowerCase();
                if(wordCounter.getOrDefault(word, new ArrayList<>()).isEmpty()){
                    words.add(word);
                }
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
//        for(String word:words){
//            System.err.println(word);
//        }
        // Записываем результат в файл
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, StandardCharsets.UTF_8))) {
            for (String word:words) {
                writer.write(word + " " + wordCounter.get(word).size() + " ");
                int indOfElem = 0;
                int size_of_array = wordCounter.get(word).size();
                for (int elem : wordCounter.get(word)) {
                    indOfElem++;
                    writer.write(String.valueOf(elem));  // Преобразование числа в строку
                    if (indOfElem != size_of_array) {
                        writer.write(" ");
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
