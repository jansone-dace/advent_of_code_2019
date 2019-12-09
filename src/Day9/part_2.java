package Day9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class part_2 {
    public static void main(String[] args) {
        String inputPath = ".\\src\\Day9\\input.txt", line = "";
        long[] codes;

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
        codes = Arrays.stream(line.split(",")).mapToLong(Long::parseLong).toArray();

        // Find the biggest number in array and extend array to that size
        int max = (int)Arrays.stream(codes).max().getAsLong();
        if (max >= codes.length) {
            long[] tmp = new long[max+1];
            for (int i = 0; i < codes.length; i++)
                tmp[i] = codes[i];
            codes = Arrays.copyOf(tmp, tmp.length);
        }

        part_1.intcodeComputer(codes, 2, 0);
    }
}
