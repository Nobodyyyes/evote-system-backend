package esmukanov.evote_system.election_management.services;

import esmukanov.evote_system.commons.models.Election;
import esmukanov.evote_system.election_management.models.request.CreateElectionRequest;
import esmukanov.evote_system.election_management.models.request.UpdateElectionOptionRequest;
import esmukanov.evote_system.election_management.models.request.UpdateElectionRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface ElectionService {

    List<Election> getAllElections();

    Election getElectionById(String electionId);

    Election createElection(CreateElectionRequest request);

    Election updateElection(String electionId, UpdateElectionRequest request);

    void deleteElection(String electionId);

    int activateElections(LocalDateTime activateTime);

    int finishActiveElections(LocalDateTime finishedTime);
}
