package esmukanov.evote_system.election_management.exceptions;

public class VoteAlreadyExistsException extends RuntimeException {

    public VoteAlreadyExistsException(String message) {
        super(message);
    }
}
