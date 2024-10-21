import MyScanner.MyScanner;
import java.io.IOException;
import java.util.ArrayList;
//1 2o 3
//-4 37777777773o
// 6o
public class Reverse {
    public static void main(String[] args) throws IOException {
        MyScanner scanner = new MyScanner(System.in);
        ArrayList<ArrayList<String>> allWords = new ArrayList<>();
        ArrayList<String> currentLine = new ArrayList<>();

        while (scanner.hasNext()) {
            String token = scanner.next();

            if ("\n".equals(token)) {
                allWords.add(currentLine);
                currentLine = new ArrayList<>();
            } else {
                currentLine.add(token);
            }
        }


        if (!currentLine.isEmpty()) {
            allWords.add(currentLine);
        }


        for (int i = allWords.size() - 1; i >= 0; i--) {
            ArrayList<String> line = allWords.get(i);
            for (int j = line.size() - 1; j >= 0; j--) {
                System.out.print(line.get(j));
                if (j > 0) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
