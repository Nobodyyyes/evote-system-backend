package esmukanov.evote_system.audit.repositories;

import esmukanov.evote_system.audit.entities.AuditLogEntity;
import esmukanov.evote_system.audit.enums.AuditAction;
import esmukanov.evote_system.audit.enums.AuditActorType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AuditLogRepository extends JpaRepository<AuditLogEntity, UUID> {

    List<AuditLogEntity> findAllByActorId(UUID actorId);

    List<AuditLogEntity> findAllByActorType(AuditActorType actorType);

    List<AuditLogEntity> findAllByAction(AuditAction action);

    List<AuditLogEntity> findAllByObjectTypeAndObjectId(String objectType, UUID objectId);
}
