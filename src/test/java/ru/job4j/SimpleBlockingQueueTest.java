package ru.job4j;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {

    @Test
    void whenOfferOneThenPoll() throws InterruptedException {
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>(1);
        List<String> rsl = new ArrayList<>();
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < 3; i++) {
                        try {
                            sbq.offer(i);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                });
        Thread consumer = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            int a = sbq.poll();
                            rsl.add("Получено: " + a);
                            if (a == 2) {
                                Thread.currentThread().interrupt();
                            }
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(rsl).isEqualTo(List.of("Получено: 0", "Получено: 1", "Получено: 2"));
    }
}