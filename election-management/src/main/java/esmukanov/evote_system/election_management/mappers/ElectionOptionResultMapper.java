package esmukanov.evote_system.election_management.mappers;

import esmukanov.evote_system.commons.entities.ElectionOptionEntity;
import esmukanov.evote_system.commons.entities.ElectionOptionResultEntity;
import esmukanov.evote_system.commons.entities.ElectionResultEntity;
import esmukanov.evote_system.election_management.models.ElectionOptionResult;
import org.springframework.stereotype.Component;

@Component
public class ElectionOptionResultMapper {

    public ElectionOptionResult toModel(ElectionOptionResultEntity entity) {
        if (entity == null) {
            return null;
        }

        return ElectionOptionResult.builder()
                .id(entity.getId())
                .optionId(entity.getOption().getId())
                .optionText(entity.getOptionText())
                .orderNumber(entity.getOrderNumber())
                .votesCount(entity.getVotesCount())
                .percentage(entity.getPercentage())
                .build();
    }

    public ElectionOptionResultEntity toEntity(ElectionOptionResult model,
                                               ElectionResultEntity result,
                                               ElectionOptionEntity option) {
        if (model == null) {
            return null;
        }

        return ElectionOptionResultEntity.builder()
                .id(model.getId())
                .result(result)
                .option(option)
                .optionText(model.getOptionText())
                .orderNumber(model.getOrderNumber())
                .votesCount(model.getVotesCount())
                .percentage(model.getPercentage())
                .build();
    }
}
