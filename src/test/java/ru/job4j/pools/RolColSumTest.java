package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;
import static ru.job4j.pools.RolColSum.asyncSum;
import static ru.job4j.pools.RolColSum.sum;

class RolColSumTest {

    @Test
    void whenGetColSumThenGet8() throws ExecutionException, InterruptedException {
        int[][] mat = {{1, 2, 3, 4},
                       {5, 6, 7, 8},
                       {9, 1, 2, 3},
                       {4, 5, 6, 7}};
        Sums[] sums = sum(mat);
        Sums[] asyncSums = asyncSum(mat);
        assertThat(sums).isEqualTo(asyncSums);
    }
}