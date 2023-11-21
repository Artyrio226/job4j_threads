package ru.job4j.io;

import java.io.*;

public class SaveContent {
    private final File file;

    public SaveContent(File file) {
        this.file = file;
    }

    public void saveContent(String content) {
        synchronized (file) {
            try (var o = new BufferedWriter(new FileWriter(file))) {
                o.write(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}