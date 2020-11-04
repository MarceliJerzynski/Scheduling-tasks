package com.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Generator {

    Random random = new Random();

    public void generate(int n, File directory) throws IOException {
        int[] p = new int[n]; //duration
        int[] r = new int[n]; //when ready
        int[] d = new int[n]; //when we want to end
        int[] w = new int[n]; //what the punishment for delay

        String fileName = directory.toPath() + "/in_jerzynski_" + n + ".txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

        writer.write(n + "\n");

        int sumP = 0;

        for(int i = 0; i < n; i++){
            p[i] = random.nextInt(9) + 1; //1 - 10 np. 1, 9 , 3
            sumP += p[i];
        }

        for(int i = 0; i < n; i++) {
            r[i] = random.nextInt(sumP); // 0 - 45 np 3, 44, 6, 22, 33
            d[i] = random.nextInt(2*p[i]) + 2*p[i] + r[i]; //np. najszybszy moment skonczyc  ----- od 2 do 3 raza dlugosci
            int window = d[i] - r[i];
            w[i] = Math.min(28/window + random.nextInt(p[i]+1), 13);
            writer.write(p[i] + " " + r[i] + " " + d[i] + " " + w[i]);
            writer.write("\n");
        }

        writer.close();
    }
}
