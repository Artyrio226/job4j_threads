package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;

class RolColSumTest {

    @Test
    void whenGetColSumThenGet8() throws ExecutionException, InterruptedException {
        int[][] mat = {{1, 2, 3, 4},
                       {5, 6, 7, 8},
                       {9, 1, 2, 3},
                       {4, 5, 6, 7}};
        RolColSum.Sums[] sums = RolColSum.sum(mat);
        RolColSum.Sums[] asyncSums = RolColSum.asyncSum(mat);
        assertThat(sums[1].getColSum()).isEqualTo(asyncSums[1].getColSum())
                .isEqualTo(14);
    }

    @Test
    void whenGetRowSumThenGet15() throws ExecutionException, InterruptedException {
        int[][] mat = {{1, 2, 3, 4},
                       {5, 6, 7, 8},
                       {9, 1, 2, 3},
                       {4, 5, 6, 7}};
        RolColSum.Sums[] sums = RolColSum.sum(mat);
        RolColSum.Sums[] asyncSums = RolColSum.asyncSum(mat);
        assertThat(sums[2].getRowSum()).isEqualTo(asyncSums[2].getRowSum())
                .isEqualTo(15);
    }
}