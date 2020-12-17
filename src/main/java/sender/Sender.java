package sender;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import fetcher.DataFetcher;
import receiver.Receiver;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {

    private final static String OUTPUT_QUEUE = "tg_msg_output";
    private final static String CONFIRM_QUEUE = "tg_votbarcode_msg_input";

    private final static String RECEIVER_USER_NAME = "barcode_receiver";
    private final static String RECEIVER_PASSWORD = "JavaBCRcvr";
    private final static String MQ_HOST = "wave3252.ddns.net";

    ConnectionFactory connectionFactory;
    Connection connection;
    Channel outputChannel;
    Channel confirmChannel;

    private Receiver receiver;


    public Sender(Receiver receiver) throws IOException, TimeoutException {
        this.receiver = receiver;
        this.connectionFactory = new ConnectionFactory();

        this.connectionFactory.setUsername(RECEIVER_USER_NAME);
        this.connectionFactory.setPassword(RECEIVER_PASSWORD);
        this.connectionFactory.setHost(MQ_HOST);
        this.connection = connectionFactory.newConnection();
        this.confirmChannel = receiver.getChannel();
        this.outputChannel = connection.createChannel();

        outputChannel.queueDeclare(OUTPUT_QUEUE, true, false, false, null);

    }

    public void send(String jsonOutput) {

//        System.out.println(jsonOutput);

        try {
            outputChannel.basicPublish("", OUTPUT_QUEUE, null, jsonOutput.getBytes());
            receiver.confirm();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
