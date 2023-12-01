package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final int from;
    private final int to;
    private final T obj;

    public ParallelSearch(T[] array, int from, int to, T obj) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.obj = obj;
    }

    private int linearSearch() {
        int index = -1;
        for (int i = from; i <= to; i++) {
            if (obj.equals(array[i])) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return linearSearch();
        }
        int mid = (from + to) / 2;
        var left = new ParallelSearch<>(array, from, mid, obj);
        var right = new ParallelSearch<>(array, mid + 1, to, obj);
        left.fork();
        right.fork();
        int rslLeft = left.join();
        int rslRight = right.join();
        return Math.max(rslLeft, rslRight);
    }

    public static <T> int search(T[] array, T obj) {
        return new ForkJoinPool().invoke(new ParallelSearch<>(array, 0, array.length - 1, obj));
    }
}
