package receiver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rabbitmq.client.*;
import util.Handler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Receiver {

    private final static String QUEUE_NAME = "tg_votbarcode_msg_input";
    private final static String RECEIVER_USER_NAME = "fetcher_service";
    private final static String RECEIVER_PASSWORD = "JavaFSvc";
    private final static String MQ_HOST = "wave3252.ddns.net";


    private final Channel channel;
    private static String receivedMessage;

    private long deliveryTag;


    private Receiver() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUsername(RECEIVER_USER_NAME);
        connectionFactory.setPassword(RECEIVER_PASSWORD);
        connectionFactory.setHost(MQ_HOST);
        Connection connection = connectionFactory.newConnection();
        this.channel = connection.createChannel();

        startMessageReceiving();
    }

    public static Receiver initReceiver() throws IOException, TimeoutException {
        return new Receiver();
    }


    public void startMessageReceiving() throws IOException {

        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages.");
        channel.basicConsume(QUEUE_NAME, false, "myConsumerTag", ConsumerNoACK());

    }


    private DefaultConsumer ConsumerNoACK() {
        return new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) {

                deliveryTag = envelope.getDeliveryTag();
                decodeMessage(body);

                try {
                    Handler.messageReceived(receivedMessage);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }


            }
        };
    }

    private void decodeMessage(byte[] body) {
        receivedMessage = new String(body, StandardCharsets.UTF_8);
    }


    public Channel getChannel() {
        return channel;
    }

    public void confirm() {
        try {
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
