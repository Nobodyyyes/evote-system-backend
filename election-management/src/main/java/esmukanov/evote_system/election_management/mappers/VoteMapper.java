package esmukanov.evote_system.election_management.mappers;

import esmukanov.evote_system.commons.entities.ElectionEntity;
import esmukanov.evote_system.commons.entities.ElectionOptionEntity;
import esmukanov.evote_system.commons.entities.VoteEntity;
import esmukanov.evote_system.election_management.models.Vote;
import org.springframework.stereotype.Component;

@Component
public class VoteMapper {

    public Vote toModel(VoteEntity entity) {
        if (entity == null) {
            return null;
        }

        return Vote.builder()
                .id(entity.getId())
                .electionId(entity.getElection().getId())
                .optionId(entity.getOption().getId())
                .voterHash(entity.getVoterHash())
                .voteHash(entity.getVoteHash())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public VoteEntity toEntity(Vote model, ElectionEntity election, ElectionOptionEntity option) {
        if (model == null) {
            return null;
        }

        return VoteEntity.builder()
                .id(model.getId())
                .election(election)
                .option(option)
                .voterHash(model.getVoterHash())
                .voteHash(model.getVoteHash())
                .createdAt(model.getCreatedAt())
                .build();
    }
}
