package com.zh.mongodb;

import java.util.*;
import java.util.stream.Collectors;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Throwables;
import com.mongodb.client.model.Projections;
import org.bson.Document;

/**
 * mongoDB相关CRUD封装
 */
public final class MongoDBDao {

	public static MongoDBDaoKeyedPool mongoDBDaoKeyedPool = new MongoDBDaoKeyedPool();

	/**
	 * 删除数据库
	 * @param connInfo
	 * @param dbName
	 */
	public static void dropDatabase(String connInfo, String dbName) {
		MongoClient client = null;
		try {
			client = mongoDBDaoKeyedPool.borrowObject(connInfo);
			client.dropDatabase(dbName);
		} catch (Exception e) {
			throw Throwables.propagate(e);
		} finally {
			if (client != null) {
				mongoDBDaoKeyedPool.returnObject(connInfo, client);
			}
		}
	}

	/**
	 * 删除集合(类似mysql的表)
	 * @param connInfo
	 * @param dbName
	 * @param collectionName
	 */
	public static void dropCollection(String connInfo, String dbName, String collectionName) {
		MongoClient client = null;
		try {
			client = mongoDBDaoKeyedPool.borrowObject(connInfo);
			client.getDatabase(dbName)
					.getCollection(collectionName)
					.drop();
		} catch (Exception e) {
			throw Throwables.propagate(e);
		} finally {
			if (client != null) {
				mongoDBDaoKeyedPool.returnObject(connInfo, client);
			}
		}
	}

	/**
	 * 向集合中插入数据
	 * @param connInfo
	 * @param dbName
	 * @param collectionName
	 * @param dataCollection
	 */
	public static void insert(String connInfo, String dbName, String collectionName, Collection<JSONObject> dataCollection) {
		MongoClient client = null;
		try {
			client = mongoDBDaoKeyedPool.borrowObject(connInfo);
			MongoCollection mongoCollection = client
					.getDatabase(dbName)
					.getCollection(collectionName);
			mongoCollection.insertMany(dataCollection.parallelStream().map(jsonObject -> new Document(jsonObject)).collect(Collectors.toList()));
		} catch (Exception e) {
			throw Throwables.propagate(e);
		} finally {
			if (client != null) {
				mongoDBDaoKeyedPool.returnObject(connInfo, client);
			}
		}
	}

	/**
	 * 查询集合中的内容，条件只支持等于
	 * @param connInfo
	 * @param dbName
	 * @param collectionName
	 * @param where  查询条件
	 * @param excludeColumns 排除的列
	 * @return
	 */
	public static List<JSONObject> query(String connInfo, String dbName, String collectionName, JSONObject where, String... excludeColumns) {
		MongoClient client = null;
		List<String> excludeColumnList = new ArrayList<>();
		excludeColumnList.add("_id"); //排除id
		if(excludeColumns != null) {
			excludeColumnList.addAll(Arrays.asList(excludeColumns));
		}
		try {
			client = mongoDBDaoKeyedPool.borrowObject(connInfo);
			MongoCollection mongoCollection = client
					.getDatabase(dbName)
					.getCollection(collectionName);

			FindIterable<Document> findResults = mongoCollection
					.find(new Document(where))
					.projection(Projections.exclude(excludeColumnList));
			MongoCursor<Document> iterator = findResults.iterator();
			List<JSONObject> results = new ArrayList<>();
			while (iterator.hasNext()) {
				results.add(new JSONObject(iterator.next()));
			}
			return results;
		} catch (Exception e) {
			throw Throwables.propagate(e);
		} finally {
			excludeColumnList.clear();
			if (client != null) {
				mongoDBDaoKeyedPool.returnObject(connInfo, client);
			}
		}
	}

	/**
	 * 从集合中删除数据，条件只支持等于
	 * @param connInfo
	 * @param dbName
	 * @param collectionName
	 * @param where
	 * @return
	 */
	public static long delete(String connInfo, String dbName, String collectionName, JSONObject where) {
		MongoClient client = null;
		try {
			client = mongoDBDaoKeyedPool.borrowObject(connInfo);
			MongoCollection mongoCollection = client
					.getDatabase(dbName)
					.getCollection(collectionName);
			return mongoCollection.deleteMany(new Document(where)).getDeletedCount();
		} catch (Exception e) {
			throw Throwables.propagate(e);
		} finally {
			if (client != null) {
				mongoDBDaoKeyedPool.returnObject(connInfo, client);
			}
		}
	}

	/**
	 * 更新集合中的数据，条件只支持等于
	 * @param connInfo
	 * @param dbName
	 * @param collectionName
	 * @param where
	 * @param newData
	 * @return
	 */
	public static long update(String connInfo, String dbName, String collectionName, JSONObject where, JSONObject newData) {
		MongoClient client = null;
		try {
			client = mongoDBDaoKeyedPool.borrowObject(connInfo);
			MongoCollection mongoCollection = client
					.getDatabase(dbName)
					.getCollection(collectionName);
			return mongoCollection.updateMany(new Document(where), new Document("$set", new Document(newData))).getModifiedCount();
		} catch (Exception e) {
			throw Throwables.propagate(e);
		} finally {
			if (client != null) {
				mongoDBDaoKeyedPool.returnObject(connInfo, client);
			}
		}
	}
}
