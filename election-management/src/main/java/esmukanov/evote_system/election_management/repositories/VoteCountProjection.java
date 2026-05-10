package esmukanov.evote_system.election_management.repositories;

import java.util.UUID;

public interface VoteCountProjection {

    UUID getOptionId();

    Long getVotesCount();
}
