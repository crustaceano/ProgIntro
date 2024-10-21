import MyScanner.MyScanner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReverseSumAbsMod {
    static final int MOD = 1_000_000_007;

    public static void main(String[] args) {
        try (MyScanner scanner = new MyScanner(System.in)) {
            // Список для хранения строк с числами
            List<List<Integer>> lines = new ArrayList<>();

            // Считываем строки чисел
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                List<Integer> numbers = new ArrayList<>();
                for (String s : line.split("\\s+")) {
                    if (!s.isEmpty()) { // Не добавляем пустые числа
                        numbers.add(Integer.parseInt(s));
                    }
                }
                lines.add(numbers);
            }

            int rowCount = lines.size();
            int[] rowSum = new int[rowCount];
            int maxColSize = 0;

            // Подсчитываем суммы по строкам и определяем максимальный размер столбца
            for (int i = 0; i < rowCount; i++) {
                List<Integer> row = lines.get(i);
                for (int num : row) {
                    rowSum[i] = (rowSum[i] + (Math.abs(num) % MOD)) % MOD;
                }
                maxColSize = Math.max(maxColSize, row.size());
            }

            int[] colSum = new int[maxColSize];

            // Подсчитываем суммы по столбцам
            for (int i = 0; i < rowCount; i++) {
                List<Integer> row = lines.get(i);
                for (int j = 0; j < row.size(); j++) {
                    colSum[j] = (colSum[j] + (Math.abs(row.get(j)) % MOD)) % MOD;
                }
            }

            // Вывод результатов
            for (int i = 0; i < rowCount; i++) {
                List<Integer> row = lines.get(i);
                for (int j = 0; j < row.size(); j++) {
                    int value = Math.abs(row.get(j));
                    int result = ((rowSum[i] + colSum[j] - value) % MOD +  MOD) % MOD;
                    System.out.print(result + " ");
                }
                System.out.println();
            }
        } catch (IOException e) {
            System.err.println("Ошибка ввода-вывода: " + e.getMessage());
        }
    }
}
