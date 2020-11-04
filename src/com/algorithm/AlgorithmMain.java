package com.algorithm;

import com.generator.Generator;

import java.io.File;

public class AlgorithmMain {

    static void validateArgs(String[] args) throws Exception {
        if (args.length != 2) {
            throw new Exception("Expected number of argument is 2. You gave me " + args.length);
        }
        if (!args[0].endsWith(".txt")) {
            throw new Exception("Expected file format is .txt. You gave me " + args[0].split("\\.")[1]);
        }

        File outputDir = new File(args[1]);
        if (!outputDir.isDirectory()) {
            throw new Exception("Second argument should be directory!");
        }
    }

    public static void main(String[] args) throws Exception {
        validateArgs(args);

        new Algorithm().run(new File(args[0]), new File(args[1]));

        System.out.println("Algorithm has ended. The result is in " + args[1] + " directory");
    }
}
