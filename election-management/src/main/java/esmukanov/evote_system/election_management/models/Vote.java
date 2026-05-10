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
public class Vote {

    UUID id;
    UUID electionId;
    UUID optionId;
    String voterHash;
    String voteHash;
    LocalDateTime createdAt;
}
