package jwt;

/**
 * @Date: 2019/7/2 15:45
 * @Description:
 */
public class InvalidJwtException extends Exception{

    public InvalidJwtException(){
        super();
    }

    public InvalidJwtException(String message){
        super(message);
    }

    public InvalidJwtException(Throwable cause){
        super(cause);
    }

    public InvalidJwtException(String message, Throwable cause){
        super(message, cause);
    }
}
