package com.zh.vertx.net;

import com.alibaba.fastjson.JSONObject;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetSocket;
import io.vertx.core.net.impl.NetSocketImpl;
import io.vertx.core.streams.Pump;
import io.vertx.core.streams.ReadStream;
import io.vertx.core.streams.WriteStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zh on 2017-08-22.
 */
public class Server {
    public static void main(String[] args) throws IOException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            ServerSocket ss = new ServerSocket(10010);
            new Thread(() -> {
                while (true) {
                    BufferedReader in = null;
                    PrintWriter out = null;
                    try {
                        Socket socket = ss.accept();
                        // 发送信息
                        out = new PrintWriter(socket.getOutputStream(), true);
                        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String paramStr = in.readLine();

                        System.out.println("接收参数:" + paramStr);

                        JSONObject paramJSON = JSONObject.parseObject(paramStr);

                        JSONObject resultJSON = new JSONObject();
                        switch(paramJSON.getString("method")) {
                            case "get": {
                                resultJSON.put("result", "get");
                                break;
                            }
                            case "put":{
                                System.out.println("put");
                                break;
                            }
                            case "contains": {
                                resultJSON.put("result", "contains");
                                break;
                            }
                            case "addAll": {
                                System.out.println("addAll");
                                break;
                            }
                        }
                        out.write(resultJSON.toJSONString() + "\r\n");
                        out.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.in.read();
    }
}
