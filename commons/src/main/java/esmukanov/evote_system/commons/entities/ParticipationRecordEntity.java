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
@Table(name = "PARTICIPATION_RECORDS")
public class ParticipationRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID uuid;

    @Column(name = "USER_ID")
    UUID userId;

    @Column(name = "ELECTION_ID")
    UUID electionId;

    @Column(name = "IS_PARTICIPATED")
    Boolean isParticipated;

    @Column(name = "PARTICIPANT_DATE")
    LocalDateTime participantDate;
}
