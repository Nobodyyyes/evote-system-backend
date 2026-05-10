package esmukanov.evote_system.hellgate.controllers;

import esmukanov.evote_system.election_management.models.response.ElectionOptionResponse;
import esmukanov.evote_system.election_management.models.request.CreateElectionOptionRequest;
import esmukanov.evote_system.election_management.models.request.UpdateElectionOptionRequest;
import esmukanov.evote_system.election_management.services.ElectionOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/election/options")
@RequiredArgsConstructor
public class ElectionOptionController {

    private final ElectionOptionService electionOptionService;

    @GetMapping("/{electionId}")
    public List<ElectionOptionResponse> getAll(@PathVariable String electionId) {
        return electionOptionService.getAllElectionOptionsByElectionId(electionId);
    }

    @PostMapping("/{electionId}")
    @ResponseStatus(HttpStatus.CREATED)
    public String createElectionOption(@PathVariable String electionId,
                                       @RequestBody CreateElectionOptionRequest request) {
        return electionOptionService.createElectionOption(electionId, request);
    }

    @PutMapping
    public void updateElectionOption(@RequestParam String electionId,
                                     @RequestParam String electionOptionId,
                                     @RequestBody UpdateElectionOptionRequest request) {
        electionOptionService.updateElectionOption(electionId, electionOptionId, request);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteElectionOption(@RequestParam String electionId,
                                     @RequestParam String electionOptionId) {
        electionOptionService.deleteElectionOption(electionId, electionOptionId);
    }
}
