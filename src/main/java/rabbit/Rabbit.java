package rabbit;

import rabbit.receiver.Receiver;
import rabbit.sender.Sender;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Rabbit {

    private static boolean initialized = false;

    MQConnection mqConnection;
    Receiver receiver;
    Sender sender;

    public Rabbit() throws IOException, TimeoutException {
        if (!initialized) {
            this.mqConnection = MQConnection.initRabbitConnection();
            this.receiver = Receiver.initReceiver(mqConnection);
            this.sender = Sender.initSender(mqConnection);
            initialized = true;
        }
    }

    public void send(String jsonString) {
        sender.send(jsonString);
    }


}
