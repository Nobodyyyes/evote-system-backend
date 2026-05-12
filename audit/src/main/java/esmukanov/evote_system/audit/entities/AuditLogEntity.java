package esmukanov.evote_system.audit.entities;

import esmukanov.evote_system.audit.enums.AuditAction;
import esmukanov.evote_system.audit.enums.AuditActorType;
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
@Table(name = "AUDIT_LOGS")
public class AuditLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACTOR_TYPE", nullable = false)
    AuditActorType actorType;

    @Column(name = "ACTOR_ID")
    UUID actorId;

    @Column(name = "ACTOR_USERNAME")
    String actorUsername;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACTION", nullable = false)
    AuditAction action;

    @Column(name = "OBJECT_TYPE", nullable = false)
    String objectType;

    @Column(name = "OBJECT_ID")
    UUID objectId;

    @Column(name = "DESCRIPTION", nullable = false, length = 1024)
    String description;

    @Column(name = "TECHNICAL_DETAILS", length = 2048)
    String technicalDetails;

    @Column(name = "CREATED_AT", nullable = false)
    LocalDateTime createdAt;
}
