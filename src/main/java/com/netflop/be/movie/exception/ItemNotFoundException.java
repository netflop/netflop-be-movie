package com.netflop.be.movie.exception;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(String messageErr, Throwable err) {
        super(messageErr, err);
    }

    public ItemNotFoundException(String messageErr) {
        super(messageErr);
    }
}
