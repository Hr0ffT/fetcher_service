package rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MQConnection {

    private final static String INPUT_QUEUE = "tg_votbarcode_msg_input";
    private final static String OUTPUT_QUEUE = "tg_votbarcode_msg_barcode";
    private final static String USER_NAME = "fetcher_service";
    private final static String PASSWORD = "JavaFSvc";
    private final static String MQ_HOST = "wave3252.ddns.net";

    private final Channel inputChannel;
    private final Channel outputChannel;

    com.rabbitmq.client.Connection connection;


    private long deliveryTag;




    private MQConnection() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUsername(USER_NAME);
        connectionFactory.setPassword(PASSWORD);
        connectionFactory.setHost(MQ_HOST);
        com.rabbitmq.client.Connection connection = connectionFactory.newConnection();
        this.inputChannel = connection.createChannel();
        this.outputChannel = connection.createChannel();

    }

    public static MQConnection initRabbitConnection() throws IOException, TimeoutException {
        return new MQConnection();

    }

    public void confirm() {
        try {
            inputChannel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getInputQueue() {
        return INPUT_QUEUE;
    }

    public static String getOutputQueue() {
        return OUTPUT_QUEUE;
    }

    public Channel getInputChannel() {
        return inputChannel;
    }

    public Channel getOutputChannel() {
        return outputChannel;
    }

    public void setDeliveryTag(long deliveryTag) {
        this.deliveryTag = deliveryTag;
    }
}
