package com.zh.vertx.cluster;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.EventBusOptions;
import io.vertx.core.eventbus.Message;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by zh on 2017-09-25.
 */
public class ClusterNode {

    private static Vertx vertx;
    private static EventBus eventBus;

    public static void main(String[] args) throws InterruptedException {
        VertxOptions vertxOptions = new VertxOptions();
        //ClusterManager
        HazelcastClusterManager clusterManager = new HazelcastClusterManager();
        vertxOptions.setClusterManager(clusterManager);

        vertxOptions.setEventBusOptions(new EventBusOptions()//
                .setTcpKeepAlive(true)//
                .setHost("localhost")//
                .setPort(8080)// bus端口（采集点恢复全在这）
                .setTcpNoDelay(true)//
                .setUsePooledBuffers(true)//
        ).setWorkerPoolSize(200)//
                .setEventLoopPoolSize(Runtime.getRuntime().availableProcessors());
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Vertx.clusteredVertx(vertxOptions, r -> {
            if(r.succeeded()) {
                vertx = r.result();
                eventBus = vertx.eventBus();
            } else {
                //failed
            }
            countDownLatch.countDown();
        });
        countDownLatch.await();
        clusterManager.getHazelcastInstance().getCluster().getLocalMember().setStringAttribute("NodeName", UUID.randomUUID().toString());
        eventBus.consumer("QUEUE", r -> {
            System.out.println("收到:" + r.body());
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
            }
            r.reply("这是返回信息……");
        });
    }
}
