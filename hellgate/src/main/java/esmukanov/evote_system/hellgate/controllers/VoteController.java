package esmukanov.evote_system.hellgate.controllers;

import esmukanov.evote_system.election_management.models.request.CastVoteRequest;
import esmukanov.evote_system.election_management.models.response.VoteAcceptedResponse;
import esmukanov.evote_system.election_management.services.VoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/elections")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping("/{electionId}/votes")
    @ResponseStatus(HttpStatus.CREATED)
    public VoteAcceptedResponse castVote(@PathVariable String electionId,
                                         @Valid @RequestBody CastVoteRequest request) {
        return voteService.castVote(electionId, request.optionId(), request.userId());
    }
}
