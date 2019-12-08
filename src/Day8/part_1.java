package Day8;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class part_1 {
    final static int PIXELS_WIDE = 25, PIXELS_TALL = 6;

    public static List<int[]> getLayers(String inputPath) throws IOException {
        List<int[]> layers = new ArrayList<>();
        List<Integer> pixels = new ArrayList<>();
        int[] layer = new int[PIXELS_WIDE * PIXELS_TALL];

        // Get file for reading pixels
        File f = new File(inputPath);
        FileReader fr = new FileReader(f);   //Creation of File Reader object
        BufferedReader br = new BufferedReader(fr);  //Creation of BufferedReader object
        int c = 0;
        while((c = br.read()) != -1)         //Read char by Char
        {
            c = Character.getNumericValue((char)c);
            if (c > -1)
                pixels.add(c);
        }

        // Turn pixels into layers
        int idx = 0;
        for (int i = 0; i < pixels.size(); i++) {
            layer[idx] = pixels.get(i);
            idx++;

            if (idx == PIXELS_TALL * PIXELS_WIDE) {
                // add layer to layer list
                layers.add(Arrays.copyOf(layer, layer.length));
                // clear layer array
                layer = new int[PIXELS_WIDE * PIXELS_TALL];
                // reset index
                idx = 0;
            }
        }

        return layers;
    }

    public static void main(String[] args) throws IOException {
        String inputPath = ".\\src\\Day8\\input.txt";
        List<int[]> layers = new ArrayList<>();
        int[] layer = new int[PIXELS_WIDE * PIXELS_TALL];
        int zeroCount, minZeroCount, minZeroLayerNumber, oneDigits, twoDigits, result;

        // Get the layers
        layers = getLayers(inputPath);

        // Find a layer that contains fewest 0 digits
        minZeroCount = Integer.MAX_VALUE;
        minZeroLayerNumber = 0;
        for (int i = 0; i < layers.size(); i++) {
            layer = layers.get(i);
            zeroCount = 0;
            for (int j = 0; j < layer.length; j++) {
                if (layer[j] == 0)
                    zeroCount++;
            }
            if (zeroCount < minZeroCount) {
                minZeroCount = zeroCount;
                minZeroLayerNumber = i;
            }
        }

        // Get result as number of 1 digits multiplied by the number of 2 digits in the layer with fewest zeros
        oneDigits = twoDigits = 0;
        layer = layers.get(minZeroLayerNumber);
        for (int i = 0; i < layer.length; i++) {
            if (layer[i] == 1)
                oneDigits++;
            else if (layer[i] == 2)
                twoDigits++;
        }
        result = oneDigits * twoDigits;

        System.out.println("Result: " + result);
    }
}
