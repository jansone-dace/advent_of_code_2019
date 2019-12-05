package Day5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class part_2 {
    public static void main(String[] args) {
        String inputPath = ".\\src\\Day5\\input.txt", line = "";
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

        part_1.doWork(codes, 5);
    }
}
