package Day8;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class part_2 {
    public static void main(String[] args) throws IOException {
        String inputPath = ".\\src\\Day8\\input.txt";
        List<int[]> layers = new ArrayList<>();
        int[] finalLayer = new int[part_1.PIXELS_WIDE * part_1.PIXELS_TALL];

        // Get the layers
        layers = part_1.getLayers(inputPath);

        // Initialize final array with 2s (transparent pixels)
        Arrays.fill(finalLayer, 2);

        // Go through all the positions and look for the first visible pixel, then add it to final layer
        for (int i = 0; i < part_1.PIXELS_WIDE * part_1.PIXELS_TALL; i++) {
            // Look for the first visible pixel in each layer
            for (int[] layer : layers) {
                if (layer[i] != 2) { // 2 is transparent
                    finalLayer[i] = layer[i];
                    break;
                }
            }
        }

        // Output the final picture pixels
        for (int i = 0; i < finalLayer.length; i++) {
            System.out.print(finalLayer[i]);
        }
    }
}
