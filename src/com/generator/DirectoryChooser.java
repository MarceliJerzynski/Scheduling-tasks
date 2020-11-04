package com.generator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class DirectoryChooser extends JPanel implements ActionListener {
    JButton go;

    JFileChooser chooser;
    String choosertitle;

    public DirectoryChooser() {
        go = new JButton("Choose where to save generated files");
        go.addActionListener(this);
        add(go);
    }

    public File getDirectory() {
        try {
        return chooser.getSelectedFile();
        } catch (Exception e){
            System.out.println("You didn't select directory, bye bye");
            System.exit(1);
            return new File("");
        }
    }


    public void actionPerformed(ActionEvent e) {

        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(choosertitle);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //
        // disable the "All files" option.
        //
        chooser.setAcceptAllFileFilterUsed(false);
        //
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
//            System.out.println("getCurrentDirectory(): "
//                    +  chooser.getCurrentDirectory());
//            System.out.println("getSelectedFile() : "
//                    +  chooser.getSelectedFile());
        }
        else {
//            System.out.println("No Selection ");
        }
    }

    public Dimension getPreferredSize(){
        return new Dimension(500, 200);
    }

}

