package com.viplogistics.exception;

/**
 * {@code ResourceNotFoundException} is a custom exception class that is thrown when a requested
 * resource (such as an entity or record) cannot be found in the system.
 * This exception extends from {@link Exception} and is typically used in situations
 * where an operation cannot be completed because the specified resource does not exist.
 */
public class ResourceNotFoundException extends Exception{

    public ResourceNotFoundException(String msg){
        super(msg);
    }
}
