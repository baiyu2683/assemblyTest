package com.zh.vertx.net;

import com.alibaba.fastjson.JSONObject;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by zh on 2017-08-22.
 */
public class Client {

    public static void main(String[] args) throws IOException {
        JSONObject jsonObject = new JSONObject();
        for(int i = 0; i < 5; i++) {
            new Thread(() -> {
                jsonObject.put("method", "get");
                String result = generateSerciceId("localhost", 10010, jsonObject.toString() + "\r\n");
                System.out.println("返回=" + result);
            }).start();
        }
        System.in.read();
    }

    public static Socket socket = null;
    public static BufferedReader in = null;
    public static PrintWriter out = null;

    public synchronized static String generateSerciceId(String ip, int port, String message) {
        while (true) {
            try {
                if (socket == null) {
                    socket = new Socket(ip, port);
                    socket.setReuseAddress(false);
                    socket.setSoTimeout(10000);
                    // 发送信息
                    out = new PrintWriter(socket.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                }
                out.println(message);
                out.flush();
                String result = in.readLine();
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                socket = null;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
