package esmukanov.evote_system.election_management.models;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ElectionResult {

    UUID id;
    UUID electionId;
    String electionTitle;
    Long totalVotes;
    String resultHash;
    LocalDateTime calculatedAt;
    @Builder.Default
    List<ElectionOptionResult> optionResults = new ArrayList<>();
}
