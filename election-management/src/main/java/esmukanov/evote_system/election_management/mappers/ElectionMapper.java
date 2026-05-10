package esmukanov.evote_system.election_management.mappers;

import esmukanov.evote_system.commons.entities.ElectionEntity;
import esmukanov.evote_system.commons.mappers.ModelMapper;
import esmukanov.evote_system.election_management.models.Election;
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
                .resultVisibilityType(entity.getResultVisibilityType())
                .resultPublished(entity.isResultPublished())
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
                .resultVisibilityType(model.getResultVisibilityType())
                .resultPublished(model.isResultPublished())
                .creatorInfo(model.getCreatorInfo())
                .accessElectionType(model.getAccessElectionType())
                .build();
    }
}
