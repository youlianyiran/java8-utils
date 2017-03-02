package com.atian.java8.utils.file;

/**
 * Created by xutiantian on 16/1/5.
 */
public class FileException extends RuntimeException {
    public FileException(String message) {
        super(message);
    }

    public FileException(String message, Throwable cause) {
        super(message, cause);
    }
}
