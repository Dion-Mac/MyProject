package com.leon.common.exception;

/**
 * @author LeonMac
 * @description
 */

public class MyException extends RuntimeException {
    public MyException(DsException dsException) {
        super(dsException.toString());
    }
}
