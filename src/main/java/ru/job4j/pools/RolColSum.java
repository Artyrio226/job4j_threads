package ru.job4j.pools;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        int n = matrix.length;
        Sums[] sums = new Sums[n];
        for (int i = 0; i < n; i++) {
            sums[i] = new Sums();
        }
        int rowSum = 0;
        int colSum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];
            }
            sums[i].setRowSum(rowSum);
            sums[i].setColSum(colSum);
            rowSum = 0;
            colSum = 0;
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int n = matrix.length;
        Sums[] sums = new Sums[n];
        for (int i = 0; i < n; i++) {
            sums[i] = new Sums();
        }
        for (int i = 0; i < n; i++) {
            sums[i].setRowSum(getRowSum(matrix, i, n).get());
            sums[i].setColSum(getColSum(matrix, i, n).get());
        }
        return sums;
    }

    private static CompletableFuture<Integer> getRowSum(int[][] matrix, int i, int n) {
        return CompletableFuture.supplyAsync(() -> {
            int sum = 0;
            for (int j = 0; j < n; j++) {
                sum += matrix[i][j];
            }
            return sum;
        });
    }

    private static CompletableFuture<Integer> getColSum(int[][] matrix, int i, int n) {
        return CompletableFuture.supplyAsync(() -> {
            int sum = 0;
            for (int j = 0; j < n; j++) {
                sum += matrix[j][i];
            }
            return sum;
        });
    }
}
