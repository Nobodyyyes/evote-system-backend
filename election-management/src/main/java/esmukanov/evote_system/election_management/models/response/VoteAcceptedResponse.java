package esmukanov.evote_system.election_management.models.response;

public record VoteAcceptedResponse(
        String electionId,
        String message
) {
}
