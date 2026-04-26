package esmukanov.evote_system.election_management.models.request;

import esmukanov.evote_system.commons.enums.AccessElectionType;

import java.time.LocalDateTime;

public record CreateElectionRequest(
        String name,
        String description,
        LocalDateTime endDateTime,
        String creatorInfo,
        AccessElectionType accessElectionType
) {
}
