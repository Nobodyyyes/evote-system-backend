package esmukanov.evote_system.election_management.exceptions;

public class ParticipationAlreadyExistsException extends RuntimeException {

    public ParticipationAlreadyExistsException(String message) {
        super(message);
    }
}
