package esmukanov.evote_system.commons.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "ELECTION_RESULTS",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_election_results_election",
                        columnNames = "election_id"
                )
        }
)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ElectionResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "election_id", nullable = false)
    ElectionEntity election;

    @Column(name = "TOTAL_VOTES")
    Long totalVotes;

    @Column(name = "TOTAL_OPTIONS")
    Integer totalOptions;

    @Column(name = "CALCULATED_AT")
    LocalDateTime calculatedAt;

    @OneToMany(
            mappedBy = "result",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    List<ElectionOptionResultEntity> optionResults = new ArrayList<>();
}
