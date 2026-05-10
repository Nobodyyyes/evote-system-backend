package esmukanov.evote_system.election_management.mappers;

import esmukanov.evote_system.commons.entities.ElectionEntity;
import esmukanov.evote_system.commons.entities.ElectionOptionEntity;
import esmukanov.evote_system.commons.entities.ElectionOptionResultEntity;
import esmukanov.evote_system.commons.entities.ElectionResultEntity;
import esmukanov.evote_system.election_management.models.ElectionResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ElectionResultMapper {

    private final ElectionOptionResultMapper optionResultMapper;

    public ElectionResult toModel(ElectionResultEntity entity) {
        if (entity == null) {
            return null;
        }

        return ElectionResult.builder()
                .id(entity.getId())
                .electionId(entity.getElection().getId())
                .electionTitle(entity.getElection().getName())
                .totalVotes(entity.getTotalVotes())
                .calculatedAt(entity.getCalculatedAt())
                .optionResults(entity.getOptionResults().stream().map(optionResultMapper::toModel).toList())
                .build();
    }

    public ElectionResultEntity toEntity(ElectionResult model,
                                         ElectionEntity election,
                                         List<ElectionOptionEntity> options) {
        if (model == null) {
            return null;
        }

        ElectionResultEntity resultEntity = ElectionResultEntity.builder()
                .id(model.getId())
                .election(election)
                .totalVotes(model.getTotalVotes())
                .totalOptions(model.getOptionResults().size())
                .calculatedAt(model.getCalculatedAt())
                .build();

        Map<UUID, ElectionOptionEntity> optionById = options
                .stream()
                .collect(Collectors.toMap(ElectionOptionEntity::getId, Function.identity()));

        List<ElectionOptionResultEntity> optionResultEntities = model.getOptionResults()
                .stream()
                .map(optionResult -> {
                    ElectionOptionEntity option = optionById.get(optionResult.getOptionId());
                    return optionResultMapper.toEntity(
                            optionResult,
                            resultEntity,
                            option
                    );
                })
                .toList();

        resultEntity.getOptionResults().addAll(optionResultEntities);

        return resultEntity;
    }
}
