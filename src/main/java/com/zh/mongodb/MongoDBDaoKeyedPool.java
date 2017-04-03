package com.zh.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

import java.sql.Connection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
/**
 * @key {host}:{port}
 * @author Administrator
 *
 */
public class MongoDBDaoKeyedPool extends GenericKeyedObjectPool<String, MongoClient> {
	public static final String hostSplitPort = ":";
	private static GenericKeyedObjectPoolConfig config = new GenericKeyedObjectPoolConfig();
	private static Set<MongoClient> objectSet = Collections.synchronizedSet(new HashSet<MongoClient>());

	static {
		config.setMaxTotal(200);
		config.setMaxTotalPerKey(200);
		config.setLifo(false);
		config.setMaxIdlePerKey(200);
		config.setMaxWaitMillis(5 * 1000);
		config.setMinEvictableIdleTimeMillis(30 * 1000);
		config.setSoftMinEvictableIdleTimeMillis(60 * 1000);
		config.setNumTestsPerEvictionRun(200);
		config.setTimeBetweenEvictionRunsMillis(60 * 1000);
		config.setTestOnBorrow(true);
	}

	public void destroy() throws Exception {
		this.close();
		for (MongoClient object : objectSet) {
			try {
				object.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public MongoDBDaoKeyedPool() {
		super(new KeyedPooledObjectFactory<String, MongoClient>() {
			// ===============================工厂======================================
			@Override
			public PooledObject<MongoClient> makeObject(String key) throws Exception {
				while (true) {
					try {
						//mongodb://[username:password@]host1[:port1][,host2[:port2],...[,hostN[:portN]]]
						MongoClientURI mongoClientURI = new MongoClientURI(key);
						MongoClient client = new MongoClient(mongoClientURI);
						objectSet.add(client);
						return new DefaultPooledObject<MongoClient>(client);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			@Override
			public void destroyObject(String key, PooledObject<MongoClient> p) throws Exception {
				try {
					p.getObject().close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					objectSet.remove(p.getObject());
				}
			}

			@Override
			public boolean validateObject(String key, PooledObject<MongoClient> p) {
				try {
					return !p.getObject().isLocked();
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}

			@Override
			public void activateObject(String key, PooledObject<MongoClient> p) throws Exception {

			}

			@Override
			public void passivateObject(String key, PooledObject<MongoClient> p) throws Exception {

			}
		}, config);
	}
}
