package esmukanov.evote_system.election_management.models.request;

import jakarta.validation.constraints.NotBlank;

public record CastVoteRequest(

        @NotBlank
        String optionId
) {
}
