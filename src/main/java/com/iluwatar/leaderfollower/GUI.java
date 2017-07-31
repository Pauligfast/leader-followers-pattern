package com.iluwatar.leaderfollower;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;


class GUI extends JFrame {
    public JTextArea textArea;
    JTextArea textArea2;
    int numOfTasks = 0;
    int numOfCabs = 0;
    final ImageIcon imageIcon = new ImageIcon("taxi.jpeg");
    GUI() {
        //  Singleton.getInstance().writeToFile("Cab:  took task");
        JPanel panel = new JPanel();
        JButton addCab = new JButton("Add Cab");
        addCab.addActionListener(e -> {
            numOfCabs++;
            textArea2.setText("Number of tasks: " + numOfTasks + "\nNumber of cabs: " + numOfCabs);
        });
        JButton addTask = new JButton("Add Task");
        addTask.addActionListener(e -> {
            numOfTasks++;
            textArea2.setText("Number of tasks: " + numOfTasks + "\nNumber of cabs: " + numOfCabs);
        });
        JButton run = new JButton("Run");
        run.addActionListener(e -> {
            try {
                Files.deleteIfExists(Paths.get("log.txt"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            ExecutorService executorService = Executors.newFixedThreadPool(numOfCabs);
            ThreadPool pool = null;
            try {
                pool = new ThreadPool(executorService, numOfTasks, numOfCabs);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            try {
                pool.start();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            try {
                executorService.awaitTermination(10000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            // executorService.shutdownNow();
            printFile();
        });

        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(e -> {
            numOfTasks = 0;
            numOfCabs = 0;
            textArea2.setText("Number of tasks: " + numOfTasks + "\nNumber of cabs: " + numOfCabs);
            textArea.setText("");

        });
         textArea = new JTextArea(20, 42) {
            Image image = imageIcon.getImage();
            {
                setOpaque(false);
            }
            public void paintComponent (Graphics g) {
                g.drawImage(image, 0, 0, this);

                setForeground(new Color(90,255,64));
                super.paintComponent(g);

            }
        };
        textArea.setForeground(Color.GREEN);
        textArea.setLineWrap(true);
        // textArea.append("console output here");
        textArea.setEditable(false);
        textArea.setOpaque(false);
        //System.out.println(Files.exists(Paths.get("taxi.jpeg")));
        JLabel label = new JLabel(new ImageIcon("taxi.jpeg"));
        label.setLayout(new BorderLayout());
        //label.setBackground(textArea.getBackground());
        textArea2 = new JTextArea(2, 10);
        JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(addCab);
        panel.add(addTask);
        panel.add(run);
        panel.add(refresh);

        textArea2.setEditable(false);
        textArea2.setText("Number of tasks: " + numOfTasks + "\nNumber of cabs: " + numOfCabs);
       // panel.add(label);
        panel.add(textArea2);
        // panel.add(textArea);
        panel.add(scrollPane);

        this.add(panel);


        this.setSize(500, 410);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Cab Manager");


    }

    public void printFile() {
        try (Stream<String> stream = Files.lines(Paths.get("log.txt"))) {
            stream.forEach(l -> {
                textArea.append(l + "\n");
                textArea.update(textArea.getGraphics());
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
