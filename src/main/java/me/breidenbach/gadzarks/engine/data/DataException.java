package me.breidenbach.gadzarks.engine.data;

/**
 * User: Kevin Breidenbach
 * Date: 10/26/13
 * time: 11:46 AM
 */
public class DataException extends Exception {

    public DataException (String message) {
        super(message);
    }

    public DataException (String message, Throwable cause) {
        super(message, cause);
    }
}
