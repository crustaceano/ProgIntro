import java.util.ArrayList;
import java.util.List;

import static java.lang.Character.END_PUNCTUATION;
import static java.lang.Character.START_PUNCTUATION;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class SumLongPunctHex {

    private static void add(List<Long> nums, String s, int l_ind, int r_ind, boolean is_16) {
        if (l_ind != -1) {
            if (!is_16) {
                nums.add(Long.parseLong(s.substring(l_ind, r_ind)));
            } else {
                nums.add(Long.parseUnsignedLong(s.substring(l_ind + 2, r_ind), 16));
            }
        }
    }

    private static List<Long> parse_nums(String s) {

        List<Long> nums = new ArrayList<Long>();
        int l_ind = -1;
        boolean is_16 = false;
        for (int i = 0; i < s.length(); i++) {
            if ((Character.getType(s.charAt(i)) != START_PUNCTUATION) && (Character.getType(s.charAt(i)) != END_PUNCTUATION) && (!Character.isWhitespace(s.charAt(i)))) {
                if (l_ind == -1) {
                    l_ind = i;
                }
                if (Character.toLowerCase(s.charAt(i)) == 'x') {
                    is_16 = true;
                }
            } else {
                add(nums, s, l_ind, i, is_16);
                is_16 = false;
                l_ind = -1;
            }
        }
        add(nums, s, l_ind, s.length(), is_16);
        return nums;
    }

    public static void main(String[] args) {
        Long sum = 0L;
        for (String arg : args) {
            arg = arg.trim();
            if (!arg.isEmpty()) {
                List<Long> nums = parse_nums(arg);
                for (Long num : nums) {
                    sum += num;
                }

            }
        }
        System.out.println(sum);
    }
}
