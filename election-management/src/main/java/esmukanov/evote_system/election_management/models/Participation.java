package esmukanov.evote_system.election_management.models;

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
public class Participation {

    UUID id;
    UUID userId;
    UUID electionId;
    boolean hasVoted;
    LocalDateTime votedAt;
}
