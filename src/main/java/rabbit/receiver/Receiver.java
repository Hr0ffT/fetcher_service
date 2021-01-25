package rabbit.receiver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.log4j.Logger;
import rabbit.MQConnection;
import util.ProcessHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Receiver {

    private static final Logger log = Logger.getLogger(Receiver.class);

    private static final String CONSUMER_TAG = System.getenv("CONSUMER_TAG");
    private static final String START_MESSAGE = "[!] Receiver initialized. Waiting for messages...";

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
        System.out.println(START_MESSAGE);
        inputChannel.basicConsume(INPUT_QUEUE, false, CONSUMER_TAG, defaultConsumer());

    }

    private DefaultConsumer defaultConsumer() {
        return new DefaultConsumer(inputChannel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) {

                rabbit.setDeliveryTag(envelope.getDeliveryTag());
                decodeMessage(body);

                try {
                    log.debug("Received a message.");
                    ProcessHandler.messageReceived(receivedMessage);
                } catch (JsonProcessingException e) {
                    log.error(e);
                }

            }
        };
    }

    private void decodeMessage(byte[] body) {
        receivedMessage = new String(body, StandardCharsets.UTF_8);
    }


}







