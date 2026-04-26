package esmukanov.evote_system.election_management.exceptions;

public class ElectionOptionNotFoundException extends RuntimeException {

    public ElectionOptionNotFoundException(String message) {
        super(message);
    }
}
