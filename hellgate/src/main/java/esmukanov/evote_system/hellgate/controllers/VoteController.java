package esmukanov.evote_system.hellgate.controllers;

import esmukanov.evote_system.election_management.models.request.CastVoteRequest;
import esmukanov.evote_system.election_management.models.response.VoteAcceptedResponse;
import esmukanov.evote_system.election_management.services.VoteService;
import esmukanov.evote_system.user_management.models.User;
import esmukanov.evote_system.user_management.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/elections")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;
    private final UserService userService;

    @PostMapping("/{electionId}/votes")
    @ResponseStatus(HttpStatus.CREATED)
    public VoteAcceptedResponse castVote(@PathVariable String electionId,
                                         @Valid @RequestBody CastVoteRequest request,
                                         Authentication authentication) {
        String username = authentication.getName();

        User currentUser = userService.getUserByUsername(username);

        voteService.castVote(
                electionId,
                request.optionId(),
                currentUser.getId().toString()
        );

        return new VoteAcceptedResponse(
                electionId,
                "Голос успешно принят"
        );
    }
}
