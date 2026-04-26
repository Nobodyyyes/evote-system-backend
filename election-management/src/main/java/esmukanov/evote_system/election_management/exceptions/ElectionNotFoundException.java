package esmukanov.evote_system.election_management.exceptions;

public class ElectionNotFoundException extends RuntimeException
{
    public ElectionNotFoundException(String message) {
        super(message);
    }
}
