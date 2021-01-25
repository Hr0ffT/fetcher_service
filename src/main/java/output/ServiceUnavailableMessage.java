package output;

public class ServiceUnavailableMessage extends FinalMessage {

    public ServiceUnavailableMessage() {
        super();
        this.text = "В данный момент сервис недоступен! Пожалуйста, повторите попытку позднее!";
    }
}
