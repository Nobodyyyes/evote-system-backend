package esmukanov.evote_system.election_management.services;

import esmukanov.evote_system.election_management.models.reponse.ElectionOptionResponse;
import esmukanov.evote_system.election_management.models.request.CreateElectionOptionRequest;
import esmukanov.evote_system.election_management.models.request.UpdateElectionOptionRequest;

import java.util.List;

public interface ElectionOptionService {

    List<ElectionOptionResponse> getAllElectionOptionsByElectionId(String electionId);

    String createElectionOption(String electionId, CreateElectionOptionRequest request);

    void updateElectionOption(String electionId, String electionOptionId, UpdateElectionOptionRequest request);

    void deleteElectionOption(String electionId, String electionOptionId);

    void deleteAllByElection(String electionId);

    long countByElectionId(String electionId);
}
