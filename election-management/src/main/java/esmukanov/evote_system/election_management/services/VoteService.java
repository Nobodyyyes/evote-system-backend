package esmukanov.evote_system.election_management.services;

import esmukanov.evote_system.election_management.models.Vote;

public interface VoteService {

    Vote castVote(String electionId, String optionId, String userId);
}
