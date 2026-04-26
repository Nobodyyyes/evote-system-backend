package esmukanov.evote_system.election_management.models.request;

public record CreateElectionOptionRequest(
        String text,
        Integer orderNumber
) {
}
