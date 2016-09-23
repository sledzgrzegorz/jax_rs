package messanger.exception;

public class DataNotFoundException extends RuntimeException {

    private final static long serialVersionUID=1L;

    public DataNotFoundException(String message){
        super(message);
    }

}
