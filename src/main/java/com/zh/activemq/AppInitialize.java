package com.zh.activemq;

/**
 * Created by zhangheng on 2017/2/4.
 */
public class AppInitialize {
    public static void main(String[] args) {
        Producer producer = new Producer();
        new Thread(producer).start();
        Consumer consumer = new Consumer();
        new Thread(consumer).start();
    }
}
