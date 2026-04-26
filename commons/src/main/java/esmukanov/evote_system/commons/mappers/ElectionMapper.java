package esmukanov.evote_system.commons.mappers;

import esmukanov.evote_system.commons.entities.ElectionEntity;
import esmukanov.evote_system.commons.models.Election;
import org.springframework.stereotype.Component;

@Component
public class ElectionMapper implements ModelMapper<Election, ElectionEntity> {

    @Override
    public Election toModel(ElectionEntity entity) {
        if (entity == null) return null;

        return Election.builder()
                .uuid(entity.getUuid())
                .name(entity.getName())
                .description(entity.getDescription())
                .startDateTime(entity.getStartDateTime())
                .endDateTime(entity.getEndDateTime())
                .createdAt(entity.getCreatedAt())
                .electionStatus(entity.getElectionStatus())
                .creatorInfo(entity.getCreatorInfo())
                .accessElectionType(entity.getAccessElectionType())
                .build();
    }

    @Override
    public ElectionEntity toEntity(Election model) {
        if (model == null) return null;

        return ElectionEntity.builder()
                .uuid(model.getUuid())
                .name(model.getName())
                .description(model.getDescription())
                .startDateTime(model.getStartDateTime())
                .endDateTime(model.getEndDateTime())
                .createdAt(model.getCreatedAt())
                .electionStatus(model.getElectionStatus())
                .creatorInfo(model.getCreatorInfo())
                .accessElectionType(model.getAccessElectionType())
                .build();
    }
}
