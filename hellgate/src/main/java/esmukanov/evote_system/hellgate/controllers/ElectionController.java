package esmukanov.evote_system.hellgate.controllers;

import esmukanov.evote_system.commons.models.Election;
import esmukanov.evote_system.election_management.models.request.CreateElectionRequest;
import esmukanov.evote_system.election_management.models.request.UpdateElectionRequest;
import esmukanov.evote_system.election_management.services.ElectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/elections")
@RequiredArgsConstructor
public class ElectionController {

    private final ElectionService electionService;

    @GetMapping
    public List<Election> getAllElections() {
        return electionService.getAllElections();
    }

    @GetMapping("/{electionId}")
    public Election getElectionById(@PathVariable String electionId) {
        return electionService.getElectionById(electionId);
    }

    @PostMapping
    public Election createElection(@RequestBody CreateElectionRequest request) {
        return electionService.createElection(request);
    }

    @PutMapping("/{electionId}")
    public Election updateElection(@PathVariable String electionId,
                                   @RequestBody UpdateElectionRequest request) {
        return electionService.updateElection(electionId, request);
    }

    @DeleteMapping("/{electionId}")
    public void deleteElection(@PathVariable String electionId) {
        electionService.deleteElection(electionId);
    }
}
