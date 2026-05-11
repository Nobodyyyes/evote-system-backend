package esmukanov.evote_system.election_management.services;

import esmukanov.evote_system.election_management.models.Election;
import esmukanov.evote_system.election_management.models.request.CreateElectionRequest;
import esmukanov.evote_system.election_management.models.request.UpdateElectionRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ElectionService {

    List<Election> getAllElections();

    Election getElectionById(String electionId);

    Election createElection(CreateElectionRequest request);

    Election updateElection(String electionId, UpdateElectionRequest request);

    void deleteElection(String electionId);

    void publishElection(String electionId);

    int activateElections(LocalDateTime activateTime);

    int finishActiveElections(LocalDateTime finishedTime);

    List<UUID> finishExpiredElections(LocalDateTime now);
}
