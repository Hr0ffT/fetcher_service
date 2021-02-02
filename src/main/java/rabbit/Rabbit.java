package rabbit;

import org.apache.log4j.Logger;
import rabbit.receiver.Receiver;
import rabbit.sender.Sender;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Rabbit {

    private static final Logger log = Logger.getLogger(Rabbit.class);

    private static final Rabbit instance = new Rabbit();

    MQConnection mqConnection;
    Sender sender;

    private Rabbit() {

            try {

                this.mqConnection = MQConnection.initRabbitConnection();
                Receiver.initReceiver(mqConnection);
                this.sender = Sender.initSender(mqConnection);

            } catch (IOException | TimeoutException e) {
                log.error(e);
            }

    }

    public void send(String jsonString) {
        sender.send(jsonString);
    }

    public static Rabbit initRabbit() {
        return instance;
    }


}
