import javax.swing.text.StyledEditorKit;
import java.io.*;
import java.util.*;

public class WordStatInput {
    static List<String> splitLineToWords(String line) {
        List<String> wordList = new ArrayList<>();
        StringBuilder curString = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            char symbol = line.charAt(i);
            if (Character.isLetter(symbol) || Character.getType(symbol) == Character.DASH_PUNCTUATION || symbol == '\'') {
                curString.append(symbol);
            } else {
                if (!curString.isEmpty()) {
                    wordList.add(curString.toString().toLowerCase());
                    curString.setLength(0);
                }
            }
        }
        if (!curString.isEmpty()) {
            wordList.add(curString.toString().toLowerCase());
            curString.setLength(0);
        }
        return wordList;
    }

    static void countWordsInLineAndRememberOrder(List<String> arrayOfWords, Map<String, Boolean> isInSetOfWords, Map<String, Integer> wordCounter, String line) {
        List<String> words = splitLineToWords(line);

        for (String word : words) {
            if (!isInSetOfWords.getOrDefault(word, false)) {
                arrayOfWords.add(word);
                isInSetOfWords.put(word, true);
            }
            wordCounter.put(word, wordCounter.getOrDefault(word, 0) + 1);
        }
    }

    static void print(PrintWriter writer, List<String> arrayOfWords, Map<String, Integer> wordCounter) {
        for (String word : arrayOfWords) {
            writer.println(word + " " + wordCounter.get(word));
        }
    }

    static boolean isFileExist(File file, String filename){
        if (!file.exists()) {
            System.out.println("Файл не существует: " + filename);
            return false;
        }
        return true;
    }
    // add feature: I should write words counts in order words ocured
    public static void main(String[] args) {
        // Проверяем, что переданы все необходимые аргументы
        if (args.length < 2) {
            System.err.println("Amount of given arguments is wrong");
            return;
        }

        // Обрабатываем возможные ошибки ввода-вывода
        try {
            File input = new File(args[0]);

            if (!isFileExist(input, args[0])) {
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(input));

            List<String> arrayOfWords = new ArrayList<>();
            Map<String, Integer> wordCounter = new HashMap<>();
            Map<String, Boolean> isInSetOfWords = new HashMap<>();
            String line;

            // Чтение строк из файла
            while ((line = reader.readLine()) != null) {
                countWordsInLineAndRememberOrder(arrayOfWords, isInSetOfWords, wordCounter, line);
            }

            // Запись результатов в выходной файл
            PrintWriter writer = new PrintWriter(args[1]);

            print(writer, arrayOfWords, wordCounter);

            // Закрываем потоки
            reader.close();
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка: файл не найден - " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода: " + e.getMessage());
        }
    }
}