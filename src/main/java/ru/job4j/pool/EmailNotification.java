package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public void emailTo(User user) {
        String subject = String.format("Notification %s to email %s.", user.username(), user.email());
        String body = String.format("Add a new event to %s.", user.username());
        pool.submit(() -> send(subject, body, user.email()));
    }

    public void send(String subject, String body, String email) { }

    public void close() {
        pool.shutdown();
    }
}