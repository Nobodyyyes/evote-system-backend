package esmukanov.evote_system.election_management.services.impl;

import esmukanov.evote_system.commons.enums.ElectionStatus;
import esmukanov.evote_system.commons.mappers.ElectionMapper;
import esmukanov.evote_system.commons.models.Election;
import esmukanov.evote_system.election_management.exceptions.ElectionNotFoundException;
import esmukanov.evote_system.election_management.models.request.CreateElectionRequest;
import esmukanov.evote_system.election_management.models.request.UpdateElectionRequest;
import esmukanov.evote_system.election_management.repositories.ElectionRepository;
import esmukanov.evote_system.election_management.services.ElectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ElectionServiceImpl implements ElectionService {

    private final ElectionRepository electionRepository;
    private final ElectionMapper electionMapper;

    @Override
    public List<Election> getAllElections() {
        return electionMapper.toModels(electionRepository.findAll());
    }

    @Override
    public Election getElectionById(String electionId) {
        return electionRepository.findById(UUID.fromString(electionId))
                .map(electionMapper::toModel)
                .orElseThrow(() -> new ElectionNotFoundException("Голосование с идентификатором [%s] не найден".formatted(electionId)));
    }

    @Override
    public Election createElection(CreateElectionRequest request) {
        Election newElection = Election.builder()
                .name(request.name())
                .description(request.description())
                .endDateTime(request.endDateTime())
                .createdAt(LocalDateTime.now())
                .electionStatus(ElectionStatus.DRAFT)
                .creatorInfo(request.creatorInfo())
                .accessElectionType(request.accessElectionType())
                .build();

        return electionMapper.toModel(electionRepository.save(electionMapper.toEntity(newElection)));
    }

    @Override
    public Election updateElection(String electionId, UpdateElectionRequest request) {
        if (electionId == null || electionId.isBlank()) {
            throw new IllegalArgumentException("Идентификатор голосования не может быть пустым");
        }

        Election existsElection = getElectionById(electionId);
        existsElection.setName(request.name());
        existsElection.setDescription(request.description());
        existsElection.setEndDateTime(request.endDateTime());
        existsElection.setAccessElectionType(request.accessElectionType());

        return electionMapper.toModel(electionRepository.save(electionMapper.toEntity(existsElection)));
    }

    @Override
    public void deleteElection(String electionId) {
        electionRepository.deleteById(UUID.fromString(electionId));
    }

    @Override
    @Transactional
    public int activateElections(LocalDateTime activateTime) {
        return electionRepository.activateScheduledElections(
                activateTime,
                ElectionStatus.SCHEDULED,
                ElectionStatus.ACTIVE
        );
    }

    @Override
    @Transactional
    public int finishActiveElections(LocalDateTime finishedTime) {
        return electionRepository.finishActiveElections(
                finishedTime,
                ElectionStatus.ACTIVE,
                ElectionStatus.COMPLETED
        );
    }
}
