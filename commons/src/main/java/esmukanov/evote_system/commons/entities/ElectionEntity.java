package esmukanov.evote_system.commons.entities;

import esmukanov.evote_system.commons.enums.AccessElectionType;
import esmukanov.evote_system.commons.enums.ElectionResultVisibilityType;
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
@Table(name = "ELECTIONS")
public class ElectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "NAME")
    String name;

    @Column(name = "DESCRIPTION")
    String description;

    @Column(name = "START_DATE_TIME")
    LocalDateTime startDateTime;

    @Column(name = "END_DATE_TIME")
    LocalDateTime endDateTime;

    @Column(name = "CREATED_AT")
    LocalDateTime createdAt;

    @Column(name = "ELECTION_STATUS")
    @Enumerated(EnumType.STRING)
    ElectionStatus electionStatus;

    @Column(name = "RESULT_VISIBILITY")
    @Enumerated(EnumType.STRING)
    ElectionResultVisibilityType resultVisibilityType = ElectionResultVisibilityType.AFTER_FINISH;

    @Column(name = "IS_RESULT_PUBLISHED")
    boolean resultPublished = false;

    @Column(name = "CREATOR_INFO")
    String creatorInfo;

    @Column(name = "ACCESS_ELECTION_TYPE")
    @Enumerated(EnumType.STRING)
    AccessElectionType accessElectionType;
}
