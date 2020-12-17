package receiver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rabbitmq.client.*;
import fetcher.DataFetcher;
import org.json.JSONException;
import util.Handler;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Receiver {

    private final static String QUEUE_NAME = "tg_votbarcode_msg_input";
    private final static String RECEIVER_USER_NAME = "barcode_receiver";
    private final static String RECEIVER_PASSWORD = "JavaBCRcvr";
    private final static String MQ_HOST = "wave3252.ddns.net";


    ConnectionFactory connectionFactory;
    Connection connection;
    Channel channel;
    String receivedMessage;
    DataFetcher dataFetcher;


    public Receiver(DataFetcher dataFetcher) throws IOException, TimeoutException {
        this.connectionFactory = new ConnectionFactory();
        this.dataFetcher = dataFetcher;
        this.connectionFactory.setUsername(RECEIVER_USER_NAME);
        this.connectionFactory.setPassword(RECEIVER_PASSWORD);
        this.connectionFactory.setHost(MQ_HOST);
        this.connection = connectionFactory.newConnection();
        this.channel = connection.createChannel();

        startMessageReceiving();
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

                decodeMessage(body);

                Handler.messageReceived( receivedMessage);


            }
        };
    }

    private void decodeMessage(byte[] body) {
        receivedMessage = new String(body, StandardCharsets.UTF_8);
    }

// TODO СДЕЛАТЬ ПОДТВЕРЖДЕНИЕ ОБ ОТПРАВКЕ, КОД НИЖЕ

    //    private DefaultConsumer ConsumerWithACK() {
    //        return new DefaultConsumer(channel) {
    //            @Override
    //            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
    //
    //                long deliveryTag = envelope.getDeliveryTag();
    //
    //                decodeMessage(body);
    //                Handler.messageReceived(receivedMessage);
    //
    //                channel.basicAck(deliveryTag, false);
    //
    //            }
    //        };
    //    }


}
