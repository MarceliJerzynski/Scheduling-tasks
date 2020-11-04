package com.algorithm;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

import static java.lang.Integer.parseInt;

public class Algorithm {

    public void run(File input, File outputDirectory) throws IOException {

        Task[] tasks = getTasks(input);
        tasks = runAlgorithm(tasks);
        int cost = getPunishment(tasks);
        String outputNameFile = getOutputNameFile(input);

        saveToFile(tasks, cost, outputDirectory, outputNameFile);
    }

    private Task[] getTasks(File input) throws FileNotFoundException {
        Scanner myReader = new Scanner(input);
        int n = parseInt(myReader.nextLine());

        Task[] tasks = new Task[n];

        int i = 0;
        while (myReader.hasNextLine()) {
            tasks[i] = new Task();
            tasks[i].index = i+1;
            String[] data = myReader.nextLine().split(" ");
            if (data.length == 4) {
                tasks[i].p = parseInt(data[0]);
                tasks[i].r = parseInt(data[1]);
                tasks[i].d = parseInt(data[2]);
                tasks[i].w = parseInt(data[3]);
                i++;
            }
        }
        return tasks;
    }

    private Task[] runAlgorithm(Task[] tasks) {

        ArrayList<Task>  result = new ArrayList<Task>();
        ArrayList<Task> delayedTasks = new ArrayList<Task>();
//        Arrays.parallelSort(tasks);
        Arrays.sort(tasks);
        int time = 0;
        for(int i = 0 ; i < tasks.length ; i++) {

            if (time < tasks[i].r) { //jesli nie mozemy wykonac
                for(int j = i+1; j < tasks.length; j++) {
                    if (tasks[j].r < time && tasks[j].d < tasks[i].r) {
                        result.add(tasks[j]); //to go robimy
                        time += tasks[j].p;
                    }
                }

                time = tasks[i].r; //to czekamy az mozemy
            }

            if (time + tasks[i].p < tasks[i].d) { //jesli sie wyrobi
                if (i != tasks.length -1) { //jesli to nie jest ostatnie zadanie
//                    if (time + tasks[i].p + Math.min(tasks[i+1].r, 0) + tasks[i+1].p < tasks[i+1].d) { //czy kolejne sie wyrobi
                    if (time + tasks[i].p + Math.min(tasks[i+1].r - time, 0) + tasks[i+1].p < tasks[i+1].d) { //czy kolejne sie wyrobi
                        result.add(tasks[i]); //to go robimy
                        time += tasks[i].p;
                        continue; //i rozpatruje kolejnego taska
                    } else { //jesli przez to ze dodam tego taska nie bede mogl zrobic kolejnego
                        if (tasks[i].w < tasks[i+1].w) { //jesli ten kolejny ma wieksza wage
                            delayedTasks.add(tasks[i]);
                        } else {
                            result.add(tasks[i]); //to go robimy
                            time += tasks[i].p;
                        }
                    }
                } else {
                    result.add(tasks[i]); //to go robimy
                    time += tasks[i].p;
                }
            } else { //jesli sie nie wyrobi
                delayedTasks.add(tasks[i]); //to idzie do opoznionych
            }
        }

        result.addAll(delayedTasks); //dodaje na koniec te ktore i tak by sie nie wyrobily

        return result.toArray(new Task[0]);

//        Arrays.parallelSort(tasks);
//        return tasks;
    }

    private String getOutputNameFile(File input) {
        String inputFileName = input.getName();
        return inputFileName.replaceFirst("in", "out");
    }

    private int getPunishment(Task[] tasks) {
        int time = 0;
        int result = 0;
        for(int i = 0;i < tasks.length; i++) { //for each task
            while(time < tasks[i].r) { //wait
                time++;
            }
            time += tasks[i].p; //end task
            if (time > tasks[i].d) {
                result += tasks[i].w;
            }
        }
        return result;
    }

    private void saveToFile(Task[] tasks, int cost, File directory, String outputFileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(directory + "/" + outputFileName));
        writer.write(cost + "\n");
        for (Task task : tasks) {
            writer.write(task.index + " ");
        }
        writer.close();

    }


}
