package ru.job4j.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    public static void validate(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Incorrect number of parameters");
        }
        try {
            new URL(args[0]).toURI();
        } catch (URISyntaxException | MalformedURLException ex) {
            throw new RuntimeException("Invalid reference");
        }
        try {
            int i = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid parameter");
        }
    }

    @Override
    public void run() {
        String extension = url.substring(url.lastIndexOf("."));
        var file = new File("tmp" + extension);
        try (var in = new URL(url).openStream();
            var out = new FileOutputStream(file)) {
            var dataBuffer = new byte[1024];
            int bytesRead;
            int totalBytes = 0;
            var startAt = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                out.write(dataBuffer, 0, bytesRead);
                totalBytes += bytesRead;
                if (totalBytes >= speed) {
                    long pause = 1000 - (System.currentTimeMillis() - startAt);
                    if (pause > 0) {
                        Thread.sleep(pause);
                    }
                    totalBytes = 0;
                    startAt = System.currentTimeMillis();
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validate(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}