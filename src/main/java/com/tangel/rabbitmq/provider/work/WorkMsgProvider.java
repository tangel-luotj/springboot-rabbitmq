package com.tangel.rabbitmq.provider.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * work模式 - 消息提供者
 *
 * @author create by luotj
 * @Date: 2020/6/18 12:27 下午
 **/
@Slf4j
public class WorkMsgProvider {

    private static final String QUEUE_NAME = "workQueue1";

    private static Connection queryConnection() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setHost("127.0.0.1");
        factory.setPassword("guest");
        factory.setUsername("guest");
        return factory.newConnection();
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = queryConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        for (int i = 1; i <= 50; i++) {
        String msg = "i am work queue Message!! index = " + i;
//        String msg = "i am work queue Message!!";
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        }
        channel.close();
        connection.close();
    }

}