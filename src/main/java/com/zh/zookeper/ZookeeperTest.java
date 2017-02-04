package com.zh.zookeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

/**
 * Created by zhangheng on 2017/2/4.
 */
public class ZookeeperTest {

    private static final int TIME_OUT = 3000;

    private static final String HOST = "localhost:2181";

    @Test
    public void test1() throws Exception{
        ZooKeeper zookeeper = new ZooKeeper(HOST, TIME_OUT, null);
        System.out.println("=========创建节点===========");
        if(zookeeper.exists("/test", false) == null) {
            zookeeper.create("/test", "znode1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        System.out.println("=============查看节点是否安装成功===============");
        System.out.println(new String(zookeeper.getData("/test", false, null)));

        System.out.println("=========修改节点的数据==========");
        String data = "zNode2";
        zookeeper.setData("/test", data.getBytes(), -1);

        System.out.println("========查看修改的节点是否成功=========");
        System.out.println(new String(zookeeper.getData("/test", false, null)));

        System.out.println("=======删除节点==========");
        zookeeper.delete("/test", -1);

        System.out.println("==========查看节点是否被删除============");
        System.out.println("节点状态：" + zookeeper.exists("/test", false));

        zookeeper.close();
    }

    @Test
    public void test2() throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181", new ExponentialBackoffRetry(1000, 3));
        client.start();
        client.create().forPath("/p1", "p1".getBytes());
        client.create().withMode(CreateMode.EPHEMERAL).forPath("/test1", "test1".getBytes());
        client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/p1/p2/p3","p1/p2/p3".getBytes());

        System.out.println(new String(client.getData().forPath("/test1")));
        client.close();
    }
}
