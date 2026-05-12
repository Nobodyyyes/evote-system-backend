package esmukanov.evote_system.election_management.services;

import esmukanov.evote_system.election_management.models.response.VoteAcceptedResponse;

public interface VoteService {

    VoteAcceptedResponse castVote(String electionId, String optionId, String userId);
}
