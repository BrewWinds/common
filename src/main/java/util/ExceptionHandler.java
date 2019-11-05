package util;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 */
@ControllerAdvice
public class ExceptionHandler {

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(HttpMessageNotReadableException.class)
    public void handle(HttpMessageNotReadableException e) {
//        logger.info("bad request", e);
//        forward("/res/page/500.jsp");
    }


    /**
     * 405 - Method Not Allowed
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @org.springframework.web.bind.annotation.ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public void handle(HttpRequestMethodNotSupportedException e) {
//        logger.info("method not allowed", e);
//        forward("/res/page/405.jsp");
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @org.springframework.web.bind.annotation.ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public void handle(HttpMediaTypeNotSupportedException e) {
//        logger.info("unsupported media type", e);
//        forward("/res/page/500.jsp");
    }

  /*  @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(UnauthorizedException.class)
    public BaseResult handle(UnauthorizedException t) {
        return BaseResult.failure("401", t.getMessage());
    }

    *//**
     * 500 - Internal Server Error
     *//*
    //@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @org.springframework.web.bind.annotation.ExceptionHandler(Throwable.class)
    public void handle(Throwable t) {
        logger.error("server error", t);
        forward("/res/page/500.jsp");
    }


    private void forward(String page){
        HttpServletRequest req = WebContext.getRequest();
        HttpServletResponse resp = WebContext.getResponse();
        try {
            req.getRequestDispatcher(page).forward(req, resp);
        } catch (ServletException | IOException e) {
//            logger.error("", e);
        }
    }*/
}
