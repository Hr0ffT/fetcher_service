package rabbit;

public class MQData {

    private String INPUT_QUEUE;

    private String MQ_EXCHANGE;

    private String ROUTING_KEY;

    private String USER_NAME;

    private String PASSWORD;

    private String MQ_HOST;

    private String AMQP_PORT;

    public MQData() {
        this.INPUT_QUEUE = System.getenv("INPUT_QUEUE");
        this.MQ_EXCHANGE = System.getenv("MQ_EXCHANGE");
        this.ROUTING_KEY = System.getenv("ROUTING_KEY");
        this.USER_NAME = System.getenv("MQ_USERNAME");
        this.PASSWORD = System.getenv("MQ_PASSWORD");
        this.MQ_HOST = System.getenv("MQ_HOST");
        this.AMQP_PORT = System.getenv("AMQP_PORT");
    }

    public void setINPUT_QUEUE(String INPUT_QUEUE) {
        this.INPUT_QUEUE = INPUT_QUEUE;
    }

    public String getINPUT_QUEUE() {
        return this.INPUT_QUEUE;
    }

    public String getMQ_EXCHANGE() {
        return MQ_EXCHANGE;
    }

    public void setMQ_EXCHANGE(String MQ_EXCHANGE) {
        this.MQ_EXCHANGE = MQ_EXCHANGE;
    }

    public void setROUTING_KEY(String ROUTING_KEY) {
        this.ROUTING_KEY = ROUTING_KEY;
    }

    public String getROUTING_KEY() {
        return this.ROUTING_KEY;
    }

    public void setUSER_NAME(String USER_NAME) {
        this.USER_NAME = USER_NAME;
    }

    public String getUSER_NAME() {
        return this.USER_NAME;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getPASSWORD() {
        return this.PASSWORD;
    }

    public void setMQ_HOST(String MQ_HOST) {
        this.MQ_HOST = MQ_HOST;
    }

    public String getMQ_HOST() {
        return this.MQ_HOST;
    }

    public String getAMQP_PORT() {
        return AMQP_PORT;
    }

    public void setAMQP_PORT(String AMQP_PORT) {
        this.AMQP_PORT = AMQP_PORT;
    }

    @Override
    public String toString() {
        return "MQData{" +
                "INPUT_QUEUE='" + INPUT_QUEUE + '\'' +
                ", MQ_EXCHANGE='" + MQ_EXCHANGE + '\'' +
                ", ROUTING_KEY='" + ROUTING_KEY + '\'' +
                ", USER_NAME='" + USER_NAME + '\'' +
                ", PASSWORD='" + PASSWORD + '\'' +
                ", MQ_HOST='" + MQ_HOST + '\'' +
                ", AMQP_PORT='" + AMQP_PORT + '\'' +
                '}';
    }
}
