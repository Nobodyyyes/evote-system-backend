package esmukanov.evote_system.audit.mappers;

import esmukanov.evote_system.audit.entities.AuditLogEntity;
import esmukanov.evote_system.audit.models.AuditLog;
import esmukanov.evote_system.commons.mappers.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuditLogMapper implements ModelMapper<AuditLog, AuditLogEntity> {

    @Override
    public AuditLog toModel(AuditLogEntity entity) {
        if (entity == null) {
            return null;
        }

        return AuditLog.builder()
                .id(entity.getId())
                .actorType(entity.getActorType())
                .actorId(entity.getActorId())
                .actorUsername(entity.getActorUsername())
                .action(entity.getAction())
                .objectType(entity.getObjectType())
                .objectId(entity.getObjectId())
                .description(entity.getDescription())
                .technicalDetails(entity.getTechnicalDetails())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    @Override
    public AuditLogEntity toEntity(AuditLog model) {
        if (model == null) {
            return null;
        }

        return AuditLogEntity.builder()
                .id(model.getId())
                .actorType(model.getActorType())
                .actorId(model.getActorId())
                .actorUsername(model.getActorUsername())
                .action(model.getAction())
                .objectType(model.getObjectType())
                .objectId(model.getObjectId())
                .description(model.getDescription())
                .technicalDetails(model.getTechnicalDetails())
                .createdAt(model.getCreatedAt())
                .build();
    }
}
