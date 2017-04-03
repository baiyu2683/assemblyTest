package com.zh.mongodb;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zh on 2017-04-02.
 */
public class MongoDBJdbc {
    private static String mongodbUrl = "mongodb://127.0.0.1:27017";
    public static void main(String[] args) {
//        List<JSONObject> list = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("name", "zhang" + 0);
//            jsonObject.put("email", "email" + 0);
//            list.add(jsonObject);
//        }
//        MongoDBDao.insert(mongodbUrl, "zhangheng", "user",  list);
//        MongoDBDao.dropCollection(mongodbUrl, "zhangheng", "user");
        JSONObject where = new JSONObject();
        where.put("name", "zhangheng");
//        List<JSONObject> list = MongoDBDao.query(mongodbUrl, "zhangheng", "user", where);
//        System.out.println("result count : " + list.size());
//        list.parallelStream().forEach(obj -> System.out.println(obj));
//        System.out.println(MongoDBDao.delete(mongodbUrl, "zhangheng", "user", where));
        JSONObject newData = new JSONObject();
        newData.put("name", "zhangheng");
        newData.put("email", "zh26831@gmail.com");
        System.out.println(MongoDBDao.update(mongodbUrl, "zhangheng", "user", where, newData));
        try {
            MongoDBDao.mongoDBDaoKeyedPool.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
