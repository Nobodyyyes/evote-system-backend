package esmukanov.evote_system.election_management.services;

import esmukanov.evote_system.election_management.models.ElectionResult;

public interface ElectionResultService {

    ElectionResult calculateResult(String electionId);

    ElectionResult getResult(String electionId);

    ElectionResult publishResult(String electionId);
}
