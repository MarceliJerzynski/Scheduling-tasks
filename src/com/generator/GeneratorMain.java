package com.generator;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.concurrent.Semaphore;

public class GeneratorMain {

    public static File openDialogAndGetDirectory() throws InterruptedException {
        Semaphore semaphore =  new Semaphore(0);

        JFrame frame = new JFrame("");
        DirectoryChooser chooser = new DirectoryChooser();
        frame.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        semaphore.release();
                    }
                }
        );
        frame.getContentPane().add(chooser,"Center");
        frame.setSize(chooser.getPreferredSize());
        frame.setVisible(true);
        semaphore.acquire();
        System.out.println("Selected directory: "
                +  chooser.getDirectory());

        return chooser.getDirectory();
    }


    public static void main(String[] args) throws Exception {
        File directory = openDialogAndGetDirectory();
        Generator generator = new Generator();
        for(int i = 50; i <= 500; i+=50){
            generator.generate(i, directory);
        }
        System.out.println("Files are ready!");
        System.exit(0);
    }
}
