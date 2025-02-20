package com.viplogistics.exception;

/**
 * {@code MemoLockedException} is a custom exception class that is thrown when an attempt
 * is made to modify a memo that is locked or otherwise cannot be changed.
 * This exception extends from {@link Exception} and is typically used to signal that the
 * requested operation cannot be performed due to the memo's locked status.
 */
public class MemoLockedException extends Exception{

    public MemoLockedException(String msg){
        super(msg);
    }
}
