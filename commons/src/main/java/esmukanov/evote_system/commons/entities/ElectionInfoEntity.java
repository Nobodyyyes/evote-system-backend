package esmukanov.evote_system.commons.entities;

import esmukanov.evote_system.commons.enums.ElectionStatus;
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
@Table(name = "ELECTION_INFOS")
public class ElectionInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID uuid;

    @Column(name = "ELECTION_ID")
    UUID electionId;

    @Column(name = "ANONYMOUS_KEY")
    String anonymousKey;

    @Column(name = "SUBMITTED_DATE")
    LocalDateTime submittedDate;

    @Column(name = "RECORD_HASH")
    String recordHash;

    @Column(name = "ELECTION_STATUS")
    @Enumerated(EnumType.STRING)
    ElectionStatus electionStatus;
}
