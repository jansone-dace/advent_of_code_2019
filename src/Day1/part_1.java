package Day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class part_1 {
    public static void main(String[] args) {
        String inputPath = ".\\src\\Day1\\input_day_1.txt";
        int fuelSum = 0, planetMass, fuelForPlanet;

        // Read mass from file
        File file = new File(inputPath);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (sc.hasNextInt()) {
            planetMass = sc.nextInt();
            // Fuel needed for a planet is its mass divided by 3, rounded down, and then substracted by 2
            fuelForPlanet = planetMass / 3 - 2;
            // total fuel needed is sum of fuel needed for eatch planet
            fuelSum += fuelForPlanet;
        }

        System.out.println("Total sum: " + fuelSum);
    }
}
