package Day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class part_2 {
    public static void main(String[] args) {
        String inputPath = ".\\src\\Day2\\input_day_2.txt", line = "";
        int[] codes;
        final int expectedOutput = 19690720;

        // Get file for reading codes
        File file = new File(inputPath);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Get all the codes into an array
        if (sc.hasNext())
            line = sc.nextLine();
        codes = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();

        outerloop:
        for (int noun = 0; noun <= 99; noun++) {
            for (int verb = 0; verb <= 99; verb++) {
                int[] tmp = Arrays.copyOf(codes, codes.length); // initialize array with "fresh" values
                // Call the operation calculation function from the first part
                part_1.doWork(tmp, noun, verb);
                if (tmp[0] == expectedOutput) {
                    System.out.println("100 * noun + verb: " + (100 * noun + verb));
                    break outerloop; // break from both loops
                }
            }
        }
    }
}
