package com.magneticraft2.common.utils.mbthings.tnjson;

/**
 * Exception on serialization error
 */
public class SerializeException extends RuntimeException {

    public SerializeException(String s) {
        super(s);
    }

    public SerializeException(String s, Throwable cause) {
        super(s, cause);
    }

}
