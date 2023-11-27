package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        int current;
        int newVal;
        do {
            current = count.get();
            newVal = current + 1;
        } while (!count.compareAndSet(current, newVal));
    }

    public int get() {
        return count.get();
    }
}
