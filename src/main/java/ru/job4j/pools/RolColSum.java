package ru.job4j.pools;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static Sums[] sum(int[][] matrix) {
        int n = matrix.length;
        Sums[] sums = new Sums[n];
        for (int i = 0; i < n; i++) {
            int rowSum = 0;
            int colSum = 0;
            for (int j = 0; j < n; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];
            }
            sums[i] = new Sums();
            sums[i].setRowSum(rowSum);
            sums[i].setColSum(colSum);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int n = matrix.length;
        Sums[] sums = new Sums[n];
        for (int i = 0; i < n; i++) {
            sums[i] = getSums(matrix, i, n).get();
        }
        return sums;
    }

    private static CompletableFuture<Sums> getSums(int[][] matrix, int i, int n) {
        return CompletableFuture.supplyAsync(() -> {
            Sums rsl = new Sums();
            int rowSum = 0;
            int colSum = 0;
            for (int j = 0; j < n; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];
            }
            rsl.setRowSum(rowSum);
            rsl.setColSum(colSum);
            return rsl;
        });
    }
}
