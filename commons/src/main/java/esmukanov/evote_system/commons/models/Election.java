package esmukanov.evote_system.commons.models;

import esmukanov.evote_system.commons.enums.AccessElectionType;
import esmukanov.evote_system.commons.enums.ElectionStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Election {

    UUID uuid;
    String name;
    String description;
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;
    ElectionStatus electionStatus;
    String creatorInfo;
    AccessElectionType accessElectionType;
}
