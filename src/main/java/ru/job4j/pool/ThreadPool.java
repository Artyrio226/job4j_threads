package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    int size = Runtime.getRuntime().availableProcessors();
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(size);

    public ThreadPool() {
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(run));
        }
        threads.forEach(Thread::start);
    }

    public void work(Runnable job) {
        try {
            tasks.offer(job);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        notifyAll();
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }

    private final Runnable run = () -> {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                while (tasks.isEmpty()) {
                    wait();
                }
                tasks.poll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    };
}
