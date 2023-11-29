package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;
import ru.job4j.concurrent.ConsoleProgress;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final static int SIZE = Runtime.getRuntime().availableProcessors();
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(SIZE);

    public ThreadPool() {
        for (int i = 0; i < SIZE; i++) {
            Runnable runnable = () -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        tasks.poll().run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            };
            threads.add(new Thread(runnable));
        }
        threads.forEach(Thread::start);
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool pool = new ThreadPool();
        for (int i = 0; i < 30; i++) {
            int finalI = i;
            pool.work(() -> System.out.println(Thread.currentThread().getName() + " " + finalI));
        }
        Thread.sleep(500);
        pool.shutdown();
    }
}
