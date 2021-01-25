package rabbit.sender;


import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.apache.log4j.Logger;
import rabbit.MQConnection;

import java.io.IOException;

public class Sender {

    private static final Logger log = Logger.getLogger(Sender.class);

    private final MQConnection connection;
    private final AMQP.BasicProperties basicProperties;
    private final Channel outputChannel;
    private final String ROUTING_KEY;
    private final String EXCHANGE;

    private Sender(MQConnection mqConnection) {
        this.connection = mqConnection;
        this.outputChannel = connection.getOutputChannel();
        this.ROUTING_KEY = connection.getRoutingKey();
        this.EXCHANGE = connection.getExchange();
        this.basicProperties = buildBasicProperties();

    }

    public static Sender initSender(MQConnection mqConnection) {
        return new Sender(mqConnection);
    }

    public void send(String jsonOutput) {

        try {
            log.debug(String.format("Publishing to Exchange '%s' with '%s' as Routing Key",EXCHANGE, ROUTING_KEY));
            outputChannel.basicPublish(EXCHANGE, ROUTING_KEY, basicProperties, jsonOutput.getBytes());
            connection.confirm();
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
