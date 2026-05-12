package esmukanov.evote_system.audit.services;

import esmukanov.evote_system.audit.enums.AuditAction;
import esmukanov.evote_system.audit.models.AuditLog;

import java.util.List;
import java.util.UUID;

public interface AuditService {

    void logUserAction(
            UUID actorId,
            String actorUsername,
            AuditAction action,
            String objectType,
            UUID objectId,
            String description
    );

    void logSystemAction(
            AuditAction action,
            String objectType,
            UUID objectId,
            String description
    );

    void logSystemAction(
            AuditAction action,
            String objectType,
            UUID objectId,
            String description,
            String technicalDetails
    );

    List<AuditLog> getLogsByObject(String objectType, UUID objectId);
}
