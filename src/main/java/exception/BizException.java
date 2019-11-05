package exception;

/**
 * @Date: 2019/9/10 20:42
 * @Description:
 */
public class BizException extends RuntimeException{

    private int code;

    public BizException(){
        super();
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(int code, String message) {
        super(message);
        this.code = code;
    }

}
