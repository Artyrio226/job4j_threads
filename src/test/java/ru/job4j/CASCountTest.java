package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CASCountTest {
    @Test
    void whenExecute2ThreadThen200() throws InterruptedException {
        CASCount cas = new CASCount();
        Runnable runnable = () -> {
            for (int i = 0; i < 100; i++) {
                cas.increment();
            }
        };
        Thread first = new Thread(runnable);
        Thread second = new Thread(runnable);
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(cas.get()).isEqualTo(200);
    }
}