package Day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class part_1 {
    public static void doWork(int[] codes, int noun, int verb) {
        int pos1, pos2, pos3, result;

        // restore the gravity assist program to the "1202 program alarm" state it had just before the last computer caught fire
        codes[1] = noun;
        codes[2] = verb;

        // Process the codes
        int i = 0;
        while (i < codes.length) {
            if (codes[i] == 99) // 99 means that the program is finished and should halt
                break;
            if (codes[i] == 1 || codes[i] == 2) { // 1 adds numbers together, 2 multiplies numbers
                // Get the positions
                pos1 = codes[i+1];
                pos2 = codes[i+2];
                pos3 = codes[i+3];
                // Add/multiply numbers from pos1 and pos2 together
                result = codes[i] == 1 ? (codes[pos1] + codes[pos2]) : (codes[pos1] * codes[pos2]);
                // Store the result in pos3
                codes[pos3] = result;
            }

            i += 4; // increment the counter
        }
    }

    public static void main(String[] args) {
        String inputPath = ".\\src\\Day2\\input_day_2.txt", line = "";
        int[] codes;

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

        doWork(codes, 12, 2);

        System.out.println("Result at position 0: " + codes[0]);
    }
}
