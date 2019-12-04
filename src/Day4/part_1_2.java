package Day4;

import java.util.HashMap;
import java.util.Map;

public class part_1_2 {
    private static boolean hasSameAdjacentDigits(int number) {
        // Turn number into String and iterate through characters
        String numString = Integer.toString(number);
        char digit, prevDigit = '\0';
        // dictionary for storing how many times adjacent digit appears
        // for solving part 2: the two adjacent matching digits are not part of a larger group of matching digits.
        Map<Character, Integer> digitUsage = new HashMap<>();

        for (int i = 0; i < numString.length(); i++) {
            digit = numString.charAt(i);
            // If this digit is the same as the previous, then there are two adjacent digits and we return true
            if (digit == prevDigit){
                if (digitUsage.containsKey(digit)) {
                    int val = digitUsage.get(digit);
                    digitUsage.replace(digit, val+1);
                }
                else {
                    // value is 2 because if we get here that means that there were 2 digits that are the same adjacent
                    // to each other
                    digitUsage.put(digit, 2);
                }
            }
            prevDigit = digit;
        }

        // See if we have digit that has appeared adjacent to the same digit only twice
        return digitUsage.containsValue(2);
    }

    private static boolean digitsDontDecrease(int number) {
        // Turn number into String and iterate through characters
        String numString = Integer.toString(number);
        char digit, prevDigit = '\0';

        for (int i = 0; i < numString.length(); i++) {
            digit = numString.charAt(i);
            // If current digit is smaller than previous one then return false
            // (It's okay to compare chars because values corresponding to numbers have same relationships)
            if (digit < prevDigit)
                return false;
            prevDigit = digit;
        }

        // If we get here then none of the digits are decreasing and we return true
        return true;
    }

    public static void main(String[] args) {
        final int rangeFrom = 137683, rangeTo = 596253;
        int passwordCount = 0;

        // Go through all the items in a range
        for (int i = rangeFrom; i <= rangeTo; i++) {
            // 1. It is a six-digit number - no need to check as interval has only 6 digit numbers
            // 2. The value is within the range given in your puzzle input - no need to check explicitly
            // 3. Two adjacent digits are the same (like 22 in 122345).
            // 4. Going from left to right, the digits never decrease;
            // they only ever increase or stay the same (like 111123 or 135679).
            // 5. [From part 2] the two adjacent matching digits are not part of a larger group of matching digits.
            if (hasSameAdjacentDigits(i) && digitsDontDecrease(i))
                passwordCount++; // if all criteria met, increase password count by one
        }

        System.out.println("Possible passwords: " + passwordCount);
    }
}
