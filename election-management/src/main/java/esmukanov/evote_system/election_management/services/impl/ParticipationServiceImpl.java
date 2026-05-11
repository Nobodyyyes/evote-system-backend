package esmukanov.evote_system.election_management.services.impl;

import esmukanov.evote_system.commons.entities.ParticipationEntity;
import esmukanov.evote_system.election_management.exceptions.ParticipationAlreadyExistsException;
import esmukanov.evote_system.election_management.mappers.ParticipationMapper;
import esmukanov.evote_system.election_management.models.Participation;
import esmukanov.evote_system.election_management.repositories.ParticipationRepository;
import esmukanov.evote_system.election_management.services.ParticipationService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParticipationServiceImpl implements ParticipationService {

    private final ParticipationRepository participationRepository;
    private final ParticipationMapper participationMapper;

    @Override
    @Transactional
    public Participation saveParticipation(UUID userId, UUID electionId, LocalDateTime votedAt) {
        if (participationRepository.existsByUserIdAndElectionId(userId, electionId)) {
            throw new ParticipationAlreadyExistsException(
                    "Факт участия пользователя в данном голосовании уже зафиксировано"
            );
        }

        Participation participation = Participation.builder()
                .userId(userId)
                .electionId(electionId)
                .hasVoted(true)
                .votedAt(votedAt)
                .build();

        try {
            ParticipationEntity savedEntity = participationRepository.save(participationMapper.toEntity(participation));
            return participationMapper.toModel(savedEntity);
        } catch (DataIntegrityViolationException exception) {
            throw new ParticipationAlreadyExistsException(
                    "Факт участия пользователя в данном голосовании уже зафиксирован"
            );
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasParticipated(UUID userId, UUID electionId) {
        return participationRepository.existsByUserIdAndElectionId(userId, electionId);
    }

    @Override
    @Transactional(readOnly = true)
    public long countParticipants(UUID electionId) {
        return participationRepository.countByElectionIdAndHasVotedTrue(electionId);
    }
}
