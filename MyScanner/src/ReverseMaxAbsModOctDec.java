import MyScanner.MyScanner;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

import static java.lang.Math.abs;

public class ReverseMaxAbsModOctDec {
    static final int MOD = 1_000_000_007;
    static final int INITIAL_CAPACITY = 100;

    public static void main(String[] args) {
        try (MyScanner scanner = new MyScanner(System.in, 1024)) {
            int rowCount = 0;
            int maxColSize = 0;
            int[][] lines = new int[INITIAL_CAPACITY][];

            int[] currentLine = new int[INITIAL_CAPACITY];
            int currentLineSize = 0;

            while (scanner.hasNext()) {
                String token = scanner.next();
                if (token.equals("\n")) {
                    if (rowCount == lines.length) {
                        lines = Arrays.copyOf(lines, lines.length * 2);
                    }
                    lines[rowCount++] = copyArray(currentLine, currentLineSize);
                    maxColSize = Math.max(maxColSize, currentLineSize);
                    currentLineSize = 0;
                } else {
                    try {
                        int number = parseNumber(token);
                        if (currentLineSize == currentLine.length) {
                            currentLine = Arrays.copyOf(currentLine, currentLine.length * 2);
                        }
                        currentLine[currentLineSize++] = number;
                    } catch (NumberFormatException e) {
                        System.err.println("Ошибка при парсинге числа: " + e.getMessage());
                    }
                }
            }
            if (currentLineSize > 0 || rowCount == 0) {
                if (rowCount == lines.length) {
                    lines = Arrays.copyOf(lines, lines.length * 2);
                }
                lines[rowCount++] = copyArray(currentLine, currentLineSize);
                maxColSize = Math.max(maxColSize, currentLineSize);
            }

            int[] originalMaxRow = new int[rowCount];
            int[] maxRowMod = new int[rowCount];
            int[] originalMaxCol = new int[maxColSize];
            int[] maxColMod = new int[maxColSize];

            for (int i = 0; i < rowCount; i++) {
                int[] row = lines[i];
                for (int j = 0; j < row.length; j++) {
                    int num = row[j];
                    int positiveModulus = abs(num) % MOD;

                    if (positiveModulus > maxRowMod[i]) {
                        maxRowMod[i] = positiveModulus;
                        originalMaxRow[i] = num;
                    }
                    if (j < maxColSize) {
                        if (positiveModulus > maxColMod[j]) {
                            maxColMod[j] = positiveModulus;
                            originalMaxCol[j] = num;
                        }
                    }
                }
            }


            for (int i = 0; i < rowCount; i++) {
                int[] row = lines[i];
                if (row.length == 0) {
                    System.out.println();
                    continue;
                }
                for (int j = 0; j < row.length; j++) {
                    int maxOriginalValue = (maxRowMod[i] > maxColMod[j])
                            ? originalMaxRow[i]
                            : originalMaxCol[j];
                    System.out.print(Integer.toOctalString(maxOriginalValue) + "o ");
                }
                System.out.println();
            }

        } catch (IOException e) {
            System.err.println("Ошибка ввода: " + e.getMessage());
        }
    }


    private static int[] copyArray(int[] original, int size) {
        return Arrays.copyOf(original, size);
    }


    private static int parseNumber(String s) {
        boolean isNegative = s.startsWith("-");
        if (isNegative) {
            s = s.substring(1);
        }

        BigInteger number;
        if (s.endsWith("o") || s.endsWith("O")) {
            String octalString = s.substring(0, s.length() - 1);
            number = new BigInteger(octalString, 8);
        } else {
            number = new BigInteger(s);
        }
        if (isNegative) {
            return -number.intValue();
        }
        return number.intValue();
    }
}
