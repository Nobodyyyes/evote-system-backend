package esmukanov.evote_system.audit.models;

import esmukanov.evote_system.audit.enums.AuditAction;
import esmukanov.evote_system.audit.enums.AuditActorType;
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
public class AuditLog {

    UUID id;
    AuditActorType actorType;
    UUID actorId;
    String actorUsername;
    AuditAction action;
    String objectType;
    UUID objectId;
    String description;
    String technicalDetails;
    LocalDateTime createdAt;
}
