package com.dev_fdm.gdouapp.spider;

/**
 * 自定义公共异常类
 * Created by Dev_fdm on 2015.
 */
public class CommonException extends Exception {

    public CommonException() {
        super();
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommonException(String message) {
        super(message);
    }

    public CommonException(Throwable cause) {
        super(cause);
    }

}
