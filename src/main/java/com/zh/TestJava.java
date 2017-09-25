package com.zh;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by zh on 2017-09-11.
 */
public class TestJava {

    /** 存储host上次访问时刻 */
    private ConcurrentHashMap<String, Long> nodeNameHostAndFrequencyMap = new ConcurrentHashMap<>();

    /**
     * 采集前阻塞对host访问（改间隔限制为频次限制）
     *
     * @return
     */
    private boolean restrictAccess(Integer frequency, String nodeNameHost) {
        int perTimes = Math.max(frequency, 1000);
        int times = perTimes / frequency;
        long now = System.currentTimeMillis() / perTimes;
        AtomicBoolean flag = new AtomicBoolean(true);
        nodeNameHostAndFrequencyMap.compute(nodeNameHost, (k, oldtime) -> {
            if (oldtime == null)
                return now * perTimes + 1;
            long difference = oldtime - now * perTimes;
            if (difference < 0) {
                return now * perTimes + 1;
            } else if (difference >= times) {
                flag.set(false);
                return oldtime;
            }
            return oldtime + 1;
        });
        if(!flag.get()) return false;
        return true;
    }
    @Test
    public void test1() {
        AtomicInteger counter = new AtomicInteger(0);
        for(;;) {
            boolean b = restrictAccess(25, "w");
            if(b) System.out.println(counter.incrementAndGet());
            else break;
        }
    }

    @Test
    public void test2() {
        List<String> list = Stream.of("one", "two", "three", "four")
                .filter(e -> e.length() > 3)
                .peek(e -> System.out.println("Filtered value: " + e))
                .map(String::toUpperCase)
                .peek(e -> System.out.println("Mapped value: " + e))
                .collect(Collectors.toList());
        System.out.println(list.toString());
    }

    @Test
    public void test3() throws InterruptedException {
        List<Person> list = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            if(i < 5) list.add(new Person(i + ""));
            else list.add(new Person(11 + ""));
        }
        Map<String, List<Person>> map = list.stream().collect(Collectors.groupingBy(Person::getName));
        for(String name : map.keySet()) {
            System.out.println(name + ":");
            System.out.println(map.get(name).toString());
        }
    }
    static class Person {
        String name;
        public Person(String name) {
            this.name = name;
        }
        public String getName() {
            return this.name;
        }
        public void setName(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }
    }

    @Test
    public void testSubList() {
        System.out.println(Math.ceil(10 / 11));
        System.out.println(3636 * 11);
    }
}
