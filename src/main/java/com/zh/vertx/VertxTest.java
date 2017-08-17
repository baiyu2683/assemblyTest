package com.zh.vertx;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;

import java.io.IOException;

/**
 * Created by zh on 2017-05-20.
 */
public class VertxTest {
    public static void main(String[] args) throws IOException {
        Vertx vertx = Vertx.vertx();
        EventBus eventBus = vertx.eventBus();
        eventBus.consumer("zhangheng", message -> {
            System.out.println("message : " + message.body());
            message.reply("aaa");
        });
//        eventBus.send("zhangheng", "first message", h -> {
//           if(h.succeeded()) {
//               System.out.println(h.result().body());
//               System.out.println("成功");
//           } else {
//               System.out.println("失败");
//           }
//        });
        eventBus.publish("zhangheng", "first messsage", new DeliveryOptions());
//        System.in.read();
    }
}
