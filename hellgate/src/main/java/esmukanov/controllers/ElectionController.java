package esmukanov.controllers;

import esmukanov.evote_system.commons.models.Election;
import esmukanov.services.ElectionService;
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
    public Election createElection(@RequestBody Election election) {
        return electionService.createElection(election);
    }

    @PutMapping
    public Election updateElection(@RequestBody Election election) {
        return electionService.updateElection(election);
    }

    @DeleteMapping("/{electionId}")
    public void deleteElection(@PathVariable String electionId) {
        electionService.deleteElection(electionId);
    }
}
