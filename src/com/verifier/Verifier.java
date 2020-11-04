package com.verifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Verifier {

    public void verify(File[] files) throws FileNotFoundException {

        File input = files[0];
        File output = files[1];
        Scanner myReader = new Scanner(input);
        int n = parseInt(myReader.nextLine().trim());

        int[] p = new int[n]; //duration
        int[] r = new int[n]; //when ready
        int[] d = new int[n]; //when we want to end
        int[] w = new int[n]; //what the punishment for delay
        int i = 0;
        while (myReader.hasNextLine()) {
            String[] data = myReader.nextLine().split(" ");
            if (data.length == 4) {
                p[i] = parseInt(data[0]);
                r[i] = parseInt(data[1]);
                d[i] = parseInt(data[2]);
                w[i] = parseInt(data[3]);
                i++;
            }
        }
        myReader.close();
        myReader = new Scanner(output);
        int[] order = new int[n];
        int result = parseInt(myReader.nextLine().trim());
        int realResult = 0;
        i = 0;
        for(String number: myReader.nextLine().split(" ")) {
            order[i] = parseInt(number)-1;
            i++;
        }
        int time = 0;
        for(i = 0;i < n; i++) { //for each task
            while(time < r[order[i]]) { //wait
                time++;
            }
            time += p[order[i]]; //end task
            if (time > d[order[i]]) {
                realResult += w[order[i]];
            }
        }


        System.out.println("-----------------");
        System.out.println("Analysing files: ");
        System.out.println(ConsoleColors.BLUE + "    Input   ---> " + input.getPath());
        System.out.println("    Output  ---> " + output.getPath());
        System.out.println("    N       ---> " + n + ConsoleColors.RESET);
        System.out.println("\n");
        System.out.println("    given punishment ---> " + result);
        System.out.println("    real punishment  --->" + realResult);
        if (realResult == result) {
            System.out.println( ConsoleColors.GREEN + "===VALID===" + ConsoleColors.RESET);
        } else {
            System.out.println( ConsoleColors.RED + "===INVALID===" + ConsoleColors.RESET);
        }

        myReader.close();
    }
}
