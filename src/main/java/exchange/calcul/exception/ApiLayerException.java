package exchange.calcul.exception;

public class ApiLayerException extends RuntimeException{

    public ApiLayerException() {
        super();
    }

    public ApiLayerException(String message) {
        super(message);
    }

    public ApiLayerException(String message, Throwable cause){
        super(message, cause);
    }

    public ApiLayerException(Throwable cause){
        super(cause);
    }
}
