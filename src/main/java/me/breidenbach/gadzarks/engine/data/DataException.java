package me.breidenbach.gadzarks.engine.data;

/**
 * User: Kevin Breidenbach
 * Date: 10/26/13
 * Time: 11:46 AM
 */
public class DataException extends Exception {

    DataException (String message) {
        super(message);
    }

    DataException (String message, Throwable cause) {
        super(message, cause);
    }
}
