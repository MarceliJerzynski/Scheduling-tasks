package com.verifier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;

public class FilesChooser extends JPanel implements ActionListener {

    JButton go;

    JFileChooser chooser;
    String choosertitle;

    public FilesChooser(String buttonText) {
        go = new JButton(buttonText);
        go.addActionListener(this);
        add(go);
    }

    public File[] getFiles() {
        try {
            return chooser.getSelectedFiles();
        } catch (Exception e){
            System.out.println("You didn't select files, bye bye");
            System.exit(1);
            return new File[] {null};
        }
    }


    public void actionPerformed(ActionEvent e) {

        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(choosertitle);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        //
        // disable the "All files" option.
        //
        chooser.setMultiSelectionEnabled(true);
        chooser.setAcceptAllFileFilterUsed(false);
        //
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
//            System.out.println("getCurrentDirectory(): "
//                    +  chooser.getCurrentDirectory());
//            System.out.println("getSelectedFile() : "
//                    + Arrays.toString(chooser.getSelectedFiles()));
        }
        else {
//            System.out.println("No Selection ");
        }
    }

    public Dimension getPreferredSize(){
        return new Dimension(500, 200);
    }
}
