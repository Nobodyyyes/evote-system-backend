package esmukanov.evote_system.election_management.mappers;

import esmukanov.evote_system.commons.entities.ParticipationEntity;
import esmukanov.evote_system.commons.mappers.ModelMapper;
import esmukanov.evote_system.election_management.models.Participation;
import org.springframework.stereotype.Component;

@Component
public class ParticipationMapper implements ModelMapper<Participation, ParticipationEntity> {

    @Override
    public Participation toModel(ParticipationEntity entity) {
        if (entity == null) {
            return null;
        }

        return Participation.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .electionId(entity.getElectionId())
                .hasVoted(entity.isHasVoted())
                .votedAt(entity.getVotedAt())
                .build();
    }

    @Override
    public ParticipationEntity toEntity(Participation model) {
        if (model == null) {
            return null;
        }

        return ParticipationEntity.builder()
                .id(model.getId())
                .userId(model.getUserId())
                .electionId(model.getElectionId())
                .hasVoted(model.isHasVoted())
                .votedAt(model.getVotedAt())
                .build();
    }
}
