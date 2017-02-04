package com.zh.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ScheduledMessage;

import javax.jms.*;
import java.util.Random;

/**
 * Created by zhangheng on 2017/2/4.
 */
public class Producer implements Runnable {
    ConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61002");
    Connection connection;
    Session session;
    Destination destination;
    MessageProducer producer;

    public void run() {
        Random random = new Random(47);
        try {
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("rdp_import_queue");
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);//非持久化
            for (int i = 1; i < 2; i++) {
                int delay = random.nextInt(10);
                String msg = "第" + i + "次发送消息, " + "delay: " + delay;
                TextMessage textMessage = session.createTextMessage(msg);
                textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delay * 1000);
                producer.send(textMessage);
            }
            producer.close();
            session.commit();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
