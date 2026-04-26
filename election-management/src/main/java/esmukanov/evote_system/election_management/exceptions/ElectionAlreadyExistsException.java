package esmukanov.evote_system.election_management.exceptions;

public class ElectionAlreadyExistsException extends RuntimeException {

    public ElectionAlreadyExistsException(String message) {
        super(message);
    }
}
