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
@Table(name = "VOTES",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_votes_election_voter",
                        columnNames = {"election_id", "voter_hash"}
                )
        }
)
public class VoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "election_id", nullable = false)
    ElectionEntity election;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "option_id", nullable = false)
    ElectionOptionEntity option;

    @Column(name = "VOTER_HASH", nullable = false)
    String voterHash;

    @Column(name = "VOTE_HASH", nullable = false)
    String voteHash;

    @Column(name = "CREATED_AT", nullable = false)
    LocalDateTime createdAt;
}
