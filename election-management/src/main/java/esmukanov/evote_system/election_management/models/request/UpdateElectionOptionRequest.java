package esmukanov.evote_system.election_management.models.request;

public record UpdateElectionOptionRequest(
        String text,
        Integer orderNumber
) {
}
