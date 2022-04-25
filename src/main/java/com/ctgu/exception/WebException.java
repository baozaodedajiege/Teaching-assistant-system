package com.ctgu.exception;

public class WebException extends Exception {

    public final WebError webError;

    public WebException(WebError webError) {
        this.webError = webError;
    }
}
