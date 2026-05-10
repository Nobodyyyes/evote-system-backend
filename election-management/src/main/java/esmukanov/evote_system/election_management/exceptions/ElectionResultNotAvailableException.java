package esmukanov.evote_system.election_management.exceptions;

public class ElectionResultNotAvailableException extends RuntimeException {

    public ElectionResultNotAvailableException(String message) {
        super(message);
    }
}
