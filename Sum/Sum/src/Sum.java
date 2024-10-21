import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Sum {
    private static List<Integer> parse_nums(String s) {
        /*
        * This function parses a string and returns a list of integers
        *
         */
        List<Integer> nums = new ArrayList<Integer>();
        String cur_s = "";
        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i)) || s.charAt(i) == '+' || s.charAt(i) == '-') {
                cur_s = cur_s + s.charAt(i);

            } else{
                if(!cur_s.isEmpty()){
                    nums.add(Integer.parseInt(cur_s));
                }
                cur_s = "";
            }
        }
        if(!cur_s.isEmpty()){
            nums.add(Integer.parseInt(cur_s));
        }
        return nums;
    }

    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        Integer sum = 0;
        for (String arg : args) {
            arg = arg.trim();
//            System.out.println(arg);
//            System.out.println('"' + arg + '"');
            if (!arg.isEmpty()) {
                List<Integer> nums = parse_nums(arg);
                for (Integer num : nums) {
                    sum += num;
                }

            }
        }

        System.out.println(sum);
//        System.out.println(args.length);
//        System.out.println(args[0]);
    }

    
}