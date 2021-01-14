package rabbit.receiver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import rabbit.MQConnection;
import util.Handler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Receiver {

    MQConnection rabbit;
    private final Channel inputChannel;
    private final String INPUT_QUEUE;

    private String receivedMessage;


    private Receiver(MQConnection mqConnection) throws IOException {
        this.rabbit = mqConnection;
        inputChannel = rabbit.getInputChannel();
        this.INPUT_QUEUE = rabbit.getInputQueue();

        startMessageReceiving();

    }

    public static Receiver initReceiver(MQConnection mqConnection) throws IOException {
        return new Receiver(mqConnection);
    }

    public void startMessageReceiving() throws IOException {

        inputChannel.queueDeclare(INPUT_QUEUE, true, false, false, null);
        System.out.println(" [*] Waiting for messages.");
        inputChannel.basicConsume(INPUT_QUEUE, false, "fetcher_service", defaultConsumer());

    }

    private DefaultConsumer defaultConsumer() {
        return new DefaultConsumer(inputChannel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) {

                rabbit.setDeliveryTag(envelope.getDeliveryTag());
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




}







