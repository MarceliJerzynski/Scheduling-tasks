package com.verifier;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Arrays;
import java.util.concurrent.Semaphore;

public class VerifierMain {
    public static File[] openDialogAndGetFiles(String buttonText) throws InterruptedException {
        Semaphore semaphore = new Semaphore(0);

        JFrame frame = new JFrame("");
        FilesChooser chooser = new FilesChooser(buttonText);
        frame.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        semaphore.release();
                    }
                }
        );
        frame.getContentPane().add(chooser, "Center");
        frame.setSize(chooser.getPreferredSize());
        frame.setVisible(true);
        semaphore.acquire();
        System.out.println("Selected files: "
                + Arrays.toString(chooser.getFiles()));

        return chooser.getFiles();
    }


    public static void main(String[] args) throws Exception {

        File[] inputs = openDialogAndGetFiles("Select input files");
        File[] outputs = openDialogAndGetFiles("Select output files");

        File[][] files = new File[inputs.length][2];
        if (inputs.length != outputs.length) {
            System.out.println("There is difference in length between inputs and outputs, bye bye");
            System.exit(1);
        }


        for(int i = 0; i < inputs.length; i++) {
            files[i][0] = inputs[i];
            for (File output : outputs) {
                if (
                        inputs[i].getPath().split("_")[2].equals(output.getPath().split("_")[2])
                ) {
                    files[i][1] = output;
                }
            }
        }


        Verifier verifier = new Verifier();
        Arrays.stream(files).forEach(pairOfFiles -> {
            try {
                verifier.verify(pairOfFiles);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

        System.exit(0);
    }
}
