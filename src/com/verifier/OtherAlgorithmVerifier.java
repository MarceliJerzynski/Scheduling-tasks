package com.verifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import static com.generator.GeneratorMain.openDialogAndGetDirectory;
import static com.verifier.VerifierMain.openDialogAndGetFiles;

public class OtherAlgorithmVerifier {

    public static void main(String[] args) throws Exception {
        File[] algorithmFiles = openDialogAndGetFiles("Select algorithm file");
        File[] inputsFile = openDialogAndGetFiles("Select input file");
        File outputs = openDialogAndGetDirectory();


        for (File algorithmFile : algorithmFiles) {
            for (File file : inputsFile) {
                long startTime = System.currentTimeMillis();

                if (algorithmFile.getAbsolutePath().endsWith(".jar")) {
                    Process ps = Runtime.getRuntime().exec(new String[]{"java", "-jar", algorithmFile.getAbsolutePath(), file.getAbsolutePath(), outputs.getAbsolutePath()});
                    ps.waitFor();
                } else {
                    if (algorithmFile.getAbsolutePath().endsWith(".py")) {
                        Process ps = Runtime.getRuntime().exec(new String[]{"python", algorithmFile.getAbsolutePath(), file.getAbsolutePath(), outputs.getAbsolutePath()});
                        ps.waitFor();
                    } else {
                        throw new Exception("Algorithm file must be in .jar or .py format!");
                    }
                }
                long stopTime = System.currentTimeMillis();
                long elapsedTime = stopTime - startTime;

                Verifier verifier = new Verifier();
                File[] pairOfFiles = new File[2];
                pairOfFiles[0] = file;
                String outputFilePath = outputs.getPath() + "\\" + file.getName().replace("in", "out");
                pairOfFiles[1] = new File(outputFilePath);
                try {
                    verifier.verify(pairOfFiles);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                System.out.println("This algorithm: " + algorithmFile.getName() + " was running for " + (double) elapsedTime/1000 + " milliseconds");
            }
        }
        System.exit(0);
    }
}
