package esmukanov.evote_system.election_management.services;

import esmukanov.evote_system.election_management.models.Participation;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ParticipationService {

    Participation saveParticipation(UUID userId, UUID electionId, LocalDateTime votedAt);

    boolean hasParticipated(UUID userId, UUID electionId);

    long countParticipants(UUID electionId);
}
