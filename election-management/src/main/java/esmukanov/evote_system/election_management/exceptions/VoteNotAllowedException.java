package esmukanov.evote_system.election_management.exceptions;

public class VoteNotAllowedException extends RuntimeException {

    public VoteNotAllowedException(String message) {
        super(message);
    }
}
