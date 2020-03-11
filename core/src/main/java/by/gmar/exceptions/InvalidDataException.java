package by.gmar.exceptions;

/**
 *
 * @author s.kosik
 */
public class InvalidDataException extends RuntimeException{
    private int errorCode;
    public InvalidDataException(){}
    public InvalidDataException(int code){
        this.errorCode = code;
    }

    public int getErrorCode() {
        return errorCode;
    }
    
}
