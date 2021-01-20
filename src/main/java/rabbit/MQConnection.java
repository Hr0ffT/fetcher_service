package rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import util.JsonHandler;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.TimeoutException;

public class MQConnection {


    private static MQData mqData;
    private final Path mqDataFilePath = Path.of("resources/mqdata.json");

    private final Channel inputChannel;
    private final Channel outputChannel;

    private long deliveryTag;

    private MQConnection() throws IOException, TimeoutException {
        mqData = readMQDataFromSysEnv();

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUsername(mqData.getUSER_NAME());
        connectionFactory.setPassword(mqData.getPASSWORD());
        connectionFactory.setHost(mqData.getMQ_HOST());
        com.rabbitmq.client.Connection connection = connectionFactory.newConnection();
        this.inputChannel = connection.createChannel();
        this.outputChannel = connection.createChannel();

    }

    private MQData readMQDataFromFile() throws IOException {
        return JsonHandler.deserializeMQData(mqDataFilePath);
    }

    private MQData readMQDataFromSysEnv() {
        return new MQData();
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

    public String getInputQueue() {
        return mqData.getINPUT_QUEUE();
    }

    public static String getOutputQueue() {
        return mqData.getOUTPUT_QUEUE();
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
