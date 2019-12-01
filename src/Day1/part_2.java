package Day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class part_2 {
    /**
     * Function for calculating fuel for a planet with specific mass
     * @param mass Mass of the planet
     * @return Amount of fuel needed to go to that planet
     */
    private static int calculateFuel(int mass) {
        // Fuel needed for a planet is its mass divided by 3, rounded down, and then substracted by 2
        int fuelForPlanet = mass / 3 - 2;
        return fuelForPlanet > 0 ? fuelForPlanet : 0;
    }

    public static void main(String[] args) {
        String inputPath = ".\\src\\Day1\\input_day_1.txt";
        int fuelSum = 0, fuel;

        // Read mass from file
        File file = new File(inputPath);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (sc.hasNextInt()) {
            fuel = sc.nextInt();

            // Calculate fuel needed for planet and fuel needed for fuel itself
            while (fuel > 0) {
                // Calculate needed fuel for planet with mass
                fuel = calculateFuel(fuel);
                // add needed fuel to the total sum
                fuelSum += fuel;
            }
        }

        System.out.println("Total sum: " + fuelSum);
    }
}
