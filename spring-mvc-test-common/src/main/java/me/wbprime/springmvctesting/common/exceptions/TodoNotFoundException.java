package me.wbprime.springmvctesting.common.exceptions;


/**
 * Class: TodoNotFoundException
 * Date: 2016/04/10 20:13
 *
 * @author Elvis Wang [mail@wbprime.me]
 */
public class TodoNotFoundException extends Exception {
    public TodoNotFoundException(final String message) {
        super(message);
    }
}
