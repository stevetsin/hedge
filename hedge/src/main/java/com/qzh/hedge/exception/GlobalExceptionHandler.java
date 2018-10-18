package com.qzh.hedge.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by qzh on 2018-9-12.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ErrorInfo jsonErrorHandler(HttpServletRequest request, Exception e) throws Exception{
        ErrorInfo error = new ErrorInfo();
        error.setErrorCode("501");
        error.setErrorMessage(e.getMessage());
        return error;
    }


}
