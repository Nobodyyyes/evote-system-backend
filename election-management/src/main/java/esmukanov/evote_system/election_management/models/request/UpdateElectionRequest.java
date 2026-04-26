package esmukanov.evote_system.election_management.models.request;

import esmukanov.evote_system.commons.enums.AccessElectionType;

public record UpdateElectionRequest(
        String name,
        String description,
        AccessElectionType accessElectionType
) {
}
