package esmukanov.evote_system.hellgate.controllers;

import esmukanov.evote_system.election_management.models.ElectionResult;
import esmukanov.evote_system.election_management.services.ElectionResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/elections")
@RequiredArgsConstructor
public class ElectionResultController {

    private final ElectionResultService electionResultService;

    @GetMapping("/{electionId}/results")
    public ElectionResult getResult(@PathVariable String electionId) {
        return electionResultService.getResult(electionId);
    }

    @PostMapping("/{electionId}/results/calculate")
    public ElectionResult calculateResult(@PathVariable String electionId) {
        return electionResultService.calculateResult(electionId);
    }

    @PostMapping("/{electionId}/results/publish")
    public ElectionResult publishResult(@PathVariable String electionId) {
        return electionResultService.publishResult(electionId);
    }
}
