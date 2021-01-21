package rabbit;

public class MQData {

    private String INPUT_QUEUE;

    private String OUTPUT_QUEUE;

    private String USER_NAME;

    private String PASSWORD;

    private String MQ_HOST;

    public MQData() {
        this.INPUT_QUEUE = System.getenv("INPUT_QUEUE");
        this.OUTPUT_QUEUE = System.getenv("OUTPUT_QUEUE");
        this.USER_NAME = System.getenv("MQ_USERNAME");
        this.PASSWORD = System.getenv("MQ_PASSWORD");
        this.MQ_HOST = System.getenv("MQ_HOST");
    }

    public void setINPUT_QUEUE(String INPUT_QUEUE) {
        this.INPUT_QUEUE = INPUT_QUEUE;
    }

    public String getINPUT_QUEUE() {
        return this.INPUT_QUEUE;
    }

    public void setOUTPUT_QUEUE(String OUTPUT_QUEUE) {
        this.OUTPUT_QUEUE = OUTPUT_QUEUE;
    }

    public String getOUTPUT_QUEUE() {
        return this.OUTPUT_QUEUE;
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

    @Override
    public String toString() {
        return "MQData{" +
                "INPUT_QUEUE='" + INPUT_QUEUE + '\'' +
                ", OUTPUT_QUEUE='" + OUTPUT_QUEUE + '\'' +
                ", USER_NAME='" + USER_NAME + '\'' +
                ", PASSWORD='" + PASSWORD + '\'' +
                ", MQ_HOST='" + MQ_HOST + '\'' +
                '}';
    }
}
