package com.zh.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangheng on 2017/2/4.
 */
public class Consumer implements Runnable {
    ConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61002");
    Connection connection;
    Session session;
    Destination destination;
    MessageConsumer consumer;

    public void run() {
        try {
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("rdp_import_queue");
            consumer = session.createConsumer(destination);
            while (true) {
                TextMessage textMessage = (TextMessage) consumer.receive();
                if(textMessage != null && !textMessage.getJMSRedelivered()) {
                    System.out.println(textMessage.getText());
                    textMessage.acknowledge();
                }
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
