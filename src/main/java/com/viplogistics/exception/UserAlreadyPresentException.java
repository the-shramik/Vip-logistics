package com.viplogistics.exception;

/**
 * {@code UserAlreadyPresentException} is a custom exception class that is thrown when an attempt is made
 * to create or register a user who already exists in the system.
 * This exception extends from {@link Exception} and is used in situations where a user already exists
 * and a new user cannot be created with the same information (e.g., username or email).
 */
public class UserAlreadyPresentException extends Exception{

    public UserAlreadyPresentException(String msg){
        super(msg);
    }
}
