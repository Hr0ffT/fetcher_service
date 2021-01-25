package rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MQConnection {

    private static final Logger log = Logger.getLogger(MQConnection.class);

    private static MQData mqData;

    private final Channel inputChannel;
    private final Channel outputChannel;

    private long deliveryTag;

    private MQConnection() throws IOException, TimeoutException {
        mqData = readMQDataFromSysEnv();

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUsername(mqData.getUSER_NAME());
        connectionFactory.setPassword(mqData.getPASSWORD());
        connectionFactory.setHost(mqData.getMQ_HOST());
        connectionFactory.setPort(Integer.parseInt(mqData.getAMQP_PORT()));
        Connection connection = connectionFactory.newConnection();
        this.inputChannel = connection.createChannel();
        this.outputChannel = connection.createChannel();

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
            log.error(e);
        }
    }

    public String getInputQueue() {
        return mqData.getINPUT_QUEUE();
    }

    public String getRoutingKey() {
        return mqData.getROUTING_KEY();
    }
    public String getExchange() {
        return mqData.getMQ_EXCHANGE();
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
