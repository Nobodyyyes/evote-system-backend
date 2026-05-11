package esmukanov.evote_system.commons.entities;

import jakarta.persistence.*;
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
@Entity
@Table(
        name = "PARTICIPATIONS",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_participation_user_election",
                        columnNames = {"user_id", "election_id"}
                )
        }
)
public class ParticipationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "USER_ID", nullable = false)
    UUID userId;

    @Column(name = "ELECTION_ID", nullable = false)
    UUID electionId;

    @Column(name = "HAS_VOTED", nullable = false)
    boolean hasVoted;

    @Column(name = "VOTED_AT", nullable = false)
    LocalDateTime votedAt;
}
