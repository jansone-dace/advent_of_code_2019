package Day9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class part_1 {

    public static void intcodeComputer(long[] codes, int input1, int input2) {
        long param1, param2, param3, result;
        int opcode, pos1 = 0, pos2 = 0, pos3 = 0;
        long inputCount = 0;
        long relativeBase;

        // Relative base by default is 0
        relativeBase = 0;

        // Process the codes
        int i = 0;
        outerloop:
        while (i < codes.length) {

            if (codes[i] <= 99) { // regular opcode
                // in regular situation opcode is just code value and all parameter modes are position modes (0)
                opcode = (int)codes[i];
                param1 = param2 = param3 = 0;
            }
            else { // new opcode
                opcode = (int)codes[i] % 100; // opcode is two rightmost digits
                param1 = (codes[i] / 100) % 10; // mode of 1st parameter is third digit from right
                param2 = (codes[i] / 1000) % 10; // mode of 2nd parameter is forth digit from right
                param3 = (codes[i] / 10000) % 10; // mode of 3rd parameter is fifth digit from right
            }

            // Get the positions
            // If parameter mode = 0 then get value stored at address,
            // if parameter mode = 1 then use that value
            // If parameter mode = 2 then use as 0 but with relative base
            if (i + 1 < codes.length) {
                if (param1 == 0)
                    pos1 = (int) codes[i+1];
                else if (param1 == 1)
                    pos1 = i + 1;
                else if (param1 == 2)
                    pos1 = (int)relativeBase + (int)codes[i+1];
            }
            if (i + 2 < codes.length) {
                if (param2 == 0)
                    pos2 = (int)codes[i+2];
                else if (param2 == 1)
                    pos2 = i + 2;
                else if (param2 == 2)
                    pos2 = (int)relativeBase + (int)codes[i+2];
            }
            if (i + 3 < codes.length) {
                if (param3 == 0)
                    pos3 = (int)codes[i+3];
                else if (param3 == 1)
                    pos3 = i + 3;
                else if (param3 == 2)
                    pos3 = (int)relativeBase + (int)codes[i+3];
            }

            switch(opcode) {
                case 99: // 99 means that the program is finished and should halt
                    break outerloop;
                case 1: // 1 adds numbers together
                case 2: // 2 multiplies numbers
                    // Add/multiply numbers with in positions pos1 and pos3 together
                    result = opcode == 1 ? (codes[pos1] + codes[pos2]) : (codes[pos1] * codes[pos2]);
                    // Store the result in pos3
                    codes[pos3] = result;
                    i += 4; // increment the counter

                    break;
                case 3: // Opcode 3 takes a single integer as input and saves it to the position given by its only parameter
                    // The TEST diagnostic program will start by requesting from the user the ID of the system to test by running
                    // an input instruction - provide it 1, the ID for the ship's air conditioner unit.
                    if (inputCount == 0) {
                        codes[pos1] = input1;
                        inputCount++;
                    }
                    else if (inputCount == 1) {
                        codes[pos1] = input2;
                        inputCount++;
                    }
                    else
                        codes[pos1] = codes[i+1];
                    i += 2; // increment the counter

                    break;
                case 4: // Opcode 4 outputs the value of its only parameter.
                    System.out.println(codes[pos1]);
                    //returnValue = codes[pos1];
                    i += 2; // increment the counter

                    break;
                case 5: // Opcode 5 is jump-if-true: if the first parameter is non-zero, it sets the
                    // instruction pointer to the value from the second parameter. Otherwise, it does nothing.
                    i = codes[pos1] != 0 ? (int)codes[pos2] : i+3;

                    break;
                case 6: // Opcode 6 is jump-if-false: if the first parameter is zero, it sets the
                    // instruction pointer to the value from the second parameter. Otherwise, it does nothing.
                    i = codes[pos1] == 0 ? (int)codes[pos2] : i+3;

                    break;
                case 7: // Opcode 7 is less than: if the first parameter is less than the second parameter,
                    // it stores 1 in the position given by the third parameter. Otherwise, it stores 0.
                    codes[pos3] = codes[pos1] < codes[pos2] ? 1 : 0;
                    i += 4; // increment the counter

                    break;
                case 8: // Opcode 8 is equals: if the first parameter is equal to the second parameter, it
                    // stores 1 in the position given by the third parameter. Otherwise, it stores 0.
                    codes[pos3] = codes[pos1] == codes[pos2] ? 1 : 0;
                    i += 4; // increment the counter

                    break;
                case 9: // Opcode 9 adjusts the relative base by the value of its only parameter.
                    relativeBase += codes[pos1];
                    i += 2; // increase the counter
                    break;
            }
        }
    }

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

        intcodeComputer(codes, 1, 0);
    }
}
