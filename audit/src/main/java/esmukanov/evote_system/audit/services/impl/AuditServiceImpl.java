package esmukanov.evote_system.audit.services.impl;

import esmukanov.evote_system.audit.entities.AuditLogEntity;
import esmukanov.evote_system.audit.enums.AuditAction;
import esmukanov.evote_system.audit.enums.AuditActorType;
import esmukanov.evote_system.audit.mappers.AuditLogMapper;
import esmukanov.evote_system.audit.models.AuditLog;
import esmukanov.evote_system.audit.repositories.AuditLogRepository;
import esmukanov.evote_system.audit.services.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditLogRepository auditLogRepository;
    private final AuditLogMapper auditLogMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logUserAction(UUID actorId,
                              String actorUsername,
                              AuditAction action,
                              String objectType,
                              UUID objectId,
                              String description) {
        saveAuditLog(
                AuditActorType.USER,
                actorId,
                actorUsername,
                action,
                objectType,
                objectId,
                description,
                null
        );
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logSystemAction(AuditAction action,
                                String objectType,
                                UUID objectId,
                                String description) {
        saveAuditLog(
                AuditActorType.SYSTEM,
                null,
                "SYSTEM",
                action,
                objectType,
                objectId,
                description,
                null
        );
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logSystemAction(AuditAction action,
                                String objectType,
                                UUID objectId,
                                String description,
                                String technicalDetails) {
        saveAuditLog(
                AuditActorType.SYSTEM,
                null,
                "SYSTEM",
                action,
                objectType,
                objectId,
                description,
                technicalDetails
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuditLog> getLogsByObject(String objectType, UUID objectId) {
        return auditLogMapper.toModels(
                auditLogRepository.findAllByObjectTypeAndObjectId(objectType, objectId)
        );
    }

    private void saveAuditLog(AuditActorType actorType,
                              UUID actorId,
                              String actorUsername,
                              AuditAction action,
                              String objectType,
                              UUID objectId,
                              String description,
                              String technicalDetails) {
        try {
            AuditLogEntity auditLog = AuditLogEntity.builder()
                    .actorType(actorType)
                    .actorId(actorId)
                    .actorUsername(actorUsername)
                    .action(action)
                    .objectType(objectType)
                    .objectId(objectId)
                    .description(description)
                    .technicalDetails(technicalDetails)
                    .createdAt(LocalDateTime.now())
                    .build();

            auditLogRepository.save(auditLog);
        } catch (Exception exception) {
            log.error("Cannot save audit log. action={}, objectType={}, objectId={}",
                    action,
                    objectType,
                    objectId,
                    exception
            );
        }
    }
}
