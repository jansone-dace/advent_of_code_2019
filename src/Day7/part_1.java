package Day7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class part_1 {

    //Generating permutation using Heap Algorithm
    // Code from https://www.geeksforgeeks.org/heaps-algorithm-for-generating-permutations/
    static void heapPermutation(int a[], int size, int n, List<int[]> result)
    {
        // if size becomes 1 then prints the obtained
        // permutation
        if (size == 1)
            result.add(Arrays.copyOf(a, n));

        for (int i=0; i<size; i++)
        {
            heapPermutation(a, size-1, n, result);

            // if size is odd, swap first and last
            // element
            if (size % 2 == 1)
            {
                int temp = a[0];
                a[0] = a[size-1];
                a[size-1] = temp;
            }

            // If size is even, swap ith and last
            // element
            else
            {
                int temp = a[i];
                a[i] = a[size-1];
                a[size-1] = temp;
            }
        }
    }


    public static int intcodeComputer(int[] codes, int input1, int input2) {
        int opcode, param1, param2, param3;
        int pos1, pos2, pos3, result;
        int returnValue = 0;
        int inputCount = 0;

        // Process the codes
        int i = 0;
        outerloop:
        while (i < codes.length) {

            if (codes[i] <= 99) { // regular opcode
                // in regular situation opcode is just code value and all parameter modes are position modes (0)
                opcode = codes[i];
                param1 = param2 = param3 = 0;
            }
            else { // new opcode
                opcode = codes[i] % 100; // opcode is two rightmost digits
                param1 = (codes[i] / 100) % 10; // mode of 1st parameter is third digit from right
                param2 = (codes[i] / 1000) % 10; // mode of 2nd parameter is forth digit from right
                param3 = (codes[i] / 10000) % 10; // mode of 3rd parameter is fifth digit from right
            }

            // Get the positions
            // If parameter mode = 0 then get value stored at address,
            // if parameter mode = 1 then use that value
            pos1 = i+1 < codes.length && param1 == 0 ? codes[i+1] : i+1;
            pos2 = i+2 < codes.length && param2 == 0 ? codes[i+2] : i+2;
            pos3 = i+3 < codes.length && param3 == 0 ? codes[i+3] : i+3;

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
                    //System.out.println(codes[pos1]);
                    returnValue = codes[pos1];
                    i += 2; // increment the counter

                    break;
                case 5: // Opcode 5 is jump-if-true: if the first parameter is non-zero, it sets the
                    // instruction pointer to the value from the second parameter. Otherwise, it does nothing.
                    i = codes[pos1] != 0 ? codes[pos2] : i+3;

                    break;
                case 6: // Opcode 6 is jump-if-false: if the first parameter is zero, it sets the
                    // instruction pointer to the value from the second parameter. Otherwise, it does nothing.
                    i = codes[pos1] == 0 ? codes[pos2] : i+3;

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
            }
        }

        return returnValue;
    }

    public static void main(String[] args) {
        String inputPath = ".\\src\\Day7\\input.txt", line = "";
        int[] codes;
        int result, maxResult;
        int[] phase_values = {0, 1, 2, 3, 4};
        List<int[]> permutations = new ArrayList<>();

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


        // Get a list of all permutations
        heapPermutation(phase_values, phase_values.length, phase_values.length, permutations);

        // Go through all permutations and try to find which gives best results
        maxResult = 0;
        for (int[] sequence : permutations) {
            // A
            result = intcodeComputer(codes, sequence[0], 0);
            // B
            result = intcodeComputer(codes, sequence[1], result);
            // C
            result = intcodeComputer(codes, sequence[2], result);
            // D
            result = intcodeComputer(codes, sequence[3], result);
            // E
            result = intcodeComputer(codes, sequence[4], result);

            if (result > maxResult)
                maxResult = result;
        }

        System.out.println("Max thruster signal: " + maxResult);
    }
}
