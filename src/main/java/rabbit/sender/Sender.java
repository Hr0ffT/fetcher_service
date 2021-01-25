package rabbit.sender;

import com.rabbitmq.client.Channel;
import org.apache.log4j.Logger;
import rabbit.MQConnection;

import java.io.IOException;

public class Sender {

    private static final Logger log = Logger.getLogger(Sender.class);

    MQConnection connection;
    private final Channel outputChannel;
    private final String ROUTING_KEY;
    private final String EXCHANGE;

    private Sender(MQConnection mqConnection) {
        this.connection = mqConnection;
        this.outputChannel = connection.getOutputChannel();
        this.ROUTING_KEY = connection.getRoutingKey();
        this.EXCHANGE = connection.getExchange();

    }

    public static Sender initSender(MQConnection mqConnection) {
        return new Sender(mqConnection);
    }

    public void send(String jsonOutput) {

        try {
            log.debug(String.format("Publishing to Exchange '%s' with '%s' as Routing Key",EXCHANGE, ROUTING_KEY));
            outputChannel.basicPublish(EXCHANGE, ROUTING_KEY, null, jsonOutput.getBytes());
            connection.confirm();
        } catch (IOException e) {
            log.error(e);
        }

    }


}
