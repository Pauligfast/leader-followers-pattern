package com.iluwatar.leaderfollower;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Singleton {
    private static final Singleton inst = new Singleton();

    private Singleton() {
        super();
    }

    public synchronized void writeToFile(String str) {

        File log = new File("log.txt");
        try {
            if (!log.exists()) {
                System.out.println("We had to make a new file.");
                log.createNewFile();
            }
            PrintWriter out = new PrintWriter(new FileWriter(log, true));
            out.append(str).append("\n");
            out.close();
        } catch (IOException e) {
            System.out.println("COULD NOT LOG!!");
        }

    }

    public static Singleton getInstance() {
        return inst;
    }

}
