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

    @Override
    public void run() {
        String extension = url.substring(url.lastIndexOf("."));
        var file = new File("tmp" + extension);
        try (var in = new URL(url).openStream();
             var out = new FileOutputStream(file)) {
            var dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                var downloadAt = System.nanoTime();
                out.write(dataBuffer, 0, bytesRead);
                Thread.sleep((1024_000_000 / (System.nanoTime() - downloadAt) / speed));
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 2) {
            throw new IllegalArgumentException("Incorrect number of parameters");
        }
        String url = args[0];
        try {
            new URL(url).toURI();
        } catch (URISyntaxException | MalformedURLException ex) {
            throw new RuntimeException("Invalid reference");
        }
        int speed;
        try {
            speed = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid parameter");
        }
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
