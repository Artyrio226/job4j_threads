package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ParallelSearchTest {

    record Obj(int year, String name) { }

    @Test
    void whenTypeIsStringInLongArrayThenGet5() {
        String[] arr = new String[14];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = String.valueOf(i);
        }
        assertThat(ParallelSearch.search(arr, "5")).isEqualTo(5);
    }

    @Test
    void whenTypeIsIntegerInShortArrayThenGet5() {
        Integer[] arr = new Integer[8];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        assertThat(ParallelSearch.search(arr, 5)).isEqualTo(5);
    }

    @Test
    void whenElementNotFound() {
        Integer[] arr = new Integer[15];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        assertThat(ParallelSearch.search(arr, 20)).isEqualTo(-1);
    }

    @Test
    void whenTypeIsObjInLongArrayThenGet11() {
        Obj[] array = {
                new Obj(17, "Oleg"),
                new Obj(27, "Igor"),
                new Obj(42, "Anna"),
                new Obj(11, "Sveta"),
                new Obj(38, "Kosty"),
                new Obj(40, "Elena"),
                new Obj(22, "Valia"),
                new Obj(14, "Gena"),
                new Obj(47, "Zina"),
                new Obj(17, "Anna"),
                new Obj(55, "Anton"),
                new Obj(34, "Irina"),
                new Obj(22, "Boris")
        };
        int index = ParallelSearch.search(array, new Obj(34, "Irina"));
        assertThat(index).isEqualTo(11);
    }

    @Test
    void whenTypeIsObjInShortArrayThenGet4() {
        Obj[] array = {
                new Obj(17, "Oleg"),
                new Obj(27, "Igor"),
                new Obj(17, "Anna"),
                new Obj(55, "Anton"),
                new Obj(34, "Irina"),
                new Obj(22, "Boris")
        };
        int index = ParallelSearch.search(array, new Obj(34, "Irina"));
        assertThat(index).isEqualTo(4);
    }

    @Test
    void whenTypeIsObjThenNotFound() {
        Obj[] array = {
                new Obj(17, "Oleg"),
                new Obj(27, "Igor"),
                new Obj(17, "Anna"),
                new Obj(55, "Anton"),
                new Obj(34, "Irina"),
                new Obj(22, "Boris")
        };
        int index = ParallelSearch.search(array, new Obj(26, "Irina"));
        assertThat(index).isEqualTo(-1);
    }
}