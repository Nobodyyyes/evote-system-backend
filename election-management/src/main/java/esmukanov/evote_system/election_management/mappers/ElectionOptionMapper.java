package esmukanov.evote_system.election_management.mappers;

import esmukanov.evote_system.commons.entities.ElectionOptionEntity;
import esmukanov.evote_system.commons.mappers.ModelMapper;
import esmukanov.evote_system.election_management.models.ElectionOption;
import org.springframework.stereotype.Component;

@Component
public class ElectionOptionMapper implements ModelMapper<ElectionOption, ElectionOptionEntity> {

    @Override
    public ElectionOption toModel(ElectionOptionEntity entity) {
        if (entity == null) return null;

        return ElectionOption.builder()
                .uuid(entity.getId())
                .electionId(entity.getElectionId())
                .optionText(entity.getOptionText())
                .orderNumber(entity.getOrderNumber())
                .build();
    }

    @Override
    public ElectionOptionEntity toEntity(ElectionOption model) {
        if (model == null) return null;

        return ElectionOptionEntity.builder()
                .id(model.getUuid())
                .electionId(model.getElectionId())
                .optionText(model.getOptionText())
                .orderNumber(model.getOrderNumber())
                .build();
    }
}
