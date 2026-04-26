package esmukanov.evote_system.election_management.services;

import esmukanov.evote_system.commons.models.Election;

import java.time.LocalDateTime;
import java.util.List;

public interface ElectionService {

    List<Election> getAllElections();

    Election getElectionById(String electionId);

    Election createElection(Election election);

    Election updateElection(Election election);

    void deleteElection(String electionId);

    int activateElections(LocalDateTime activateTime);

    int finishActiveElections(LocalDateTime finishedTime);
}
