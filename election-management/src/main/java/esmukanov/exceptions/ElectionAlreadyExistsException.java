package esmukanov.exceptions;

public class ElectionAlreadyExistsException extends RuntimeException {

    public ElectionAlreadyExistsException(String message) {
        super(message);
    }
}
