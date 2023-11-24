package ru.job4j;

public class CountBarrier {
    private final Object monitor = this;
    private final int total;
    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            count++;
            notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            try {
                while (count < total) {
                    wait();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        CountBarrier barrier = new CountBarrier(5);
        Thread first = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    barrier.await();
                    System.out.println(Thread.currentThread().getName() + " continued");
                },
                "First"
        );
        Thread second = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
                        barrier.count();
                        System.out.println(Thread.currentThread().getName() + " increased the counter");
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                },
                "Second"
        );
        Thread third = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
                        barrier.count();
                        System.out.println(Thread.currentThread().getName() + " increased the counter");
                        try {
                            Thread.sleep(400);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                },
                "Third"
        );
        first.start();
        second.start();
        third.start();
    }
}
