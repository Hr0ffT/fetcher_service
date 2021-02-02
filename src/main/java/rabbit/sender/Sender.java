package rabbit.sender;


import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.apache.log4j.Logger;
import rabbit.MQConnection;

import java.io.IOException;

public class Sender {

    private static final Logger log = Logger.getLogger(Sender.class);

    private static final Sender instance = new Sender();

    private static MQConnection connection;
    private static AMQP.BasicProperties basicProperties;
    private static Channel outputChannel;
    private static String ROUTING_KEY;
    private static String EXCHANGE;

    private Sender() {


    }

    public static Sender initSender(MQConnection mqConnection) {
        connection = mqConnection;
        outputChannel = connection.getOutputChannel();
        ROUTING_KEY = connection.getRoutingKey();
        EXCHANGE = connection.getExchange();
        basicProperties = buildBasicProperties();

        return instance;
    }

    public void send(String jsonOutput) {

        try {
            log.debug(String.format("Publishing to Exchange '%s' with '%s' as Routing Key",EXCHANGE, ROUTING_KEY));
            outputChannel.basicPublish(EXCHANGE, ROUTING_KEY, basicProperties, jsonOutput.getBytes());
            connection.confirm();
            System.out.println(" - Published a message");
        } catch (IOException e) {
            log.error(e);
        }

    }

    private static AMQP.BasicProperties buildBasicProperties() {

        return new AMQP.BasicProperties.Builder()
                .contentType("application/json")
                .contentEncoding("UTF8")
                .deliveryMode(Integer.valueOf(System.getenv("MQ_DELIVERY_MODE")))
                .build();

    }




}
