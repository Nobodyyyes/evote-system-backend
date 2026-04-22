package esmukanov.services;

import esmukanov.evote_system.commons.models.Election;

import java.util.List;

public interface ElectionService {

    List<Election> getAllElections();

    Election getElectionById(String electionId);

    Election createElection(Election election);

    Election updateElection(Election election);

    void deleteElection(String electionId);
}
