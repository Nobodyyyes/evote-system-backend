package esmukanov.evote_system.hellgate.controllers;

import esmukanov.evote_system.election_management.models.request.CreateElectionOptionRequest;
import esmukanov.evote_system.election_management.models.request.UpdateElectionOptionRequest;
import esmukanov.evote_system.election_management.models.response.ElectionOptionResponse;
import esmukanov.evote_system.election_management.services.ElectionOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/elections/{electionId}/options")
@RequiredArgsConstructor
public class ElectionOptionController {

    private final ElectionOptionService electionOptionService;

    @GetMapping
    public List<ElectionOptionResponse> getAll(@PathVariable String electionId) {
        return electionOptionService.getAllElectionOptionsByElectionId(electionId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createElectionOption(@PathVariable String electionId,
                                       @RequestBody CreateElectionOptionRequest request) {
        return electionOptionService.createElectionOption(electionId, request);
    }

    @PutMapping("/{electionOptionId}")
    public void updateElectionOption(@PathVariable String electionId,
                                     @PathVariable String electionOptionId,
                                     @RequestBody UpdateElectionOptionRequest request) {
        electionOptionService.updateElectionOption(electionId, electionOptionId, request);
    }

    @DeleteMapping("/{electionOptionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteElectionOption(@PathVariable String electionId,
                                     @PathVariable String electionOptionId) {
        electionOptionService.deleteElectionOption(electionId, electionOptionId);
    }
}
