package esmukanov.evote_system.election_management.services.impl;

import esmukanov.evote_system.commons.enums.ElectionStatus;
import esmukanov.evote_system.election_management.exceptions.ElectionNotFoundException;
import esmukanov.evote_system.election_management.mappers.ElectionMapper;
import esmukanov.evote_system.election_management.models.Election;
import esmukanov.evote_system.election_management.models.request.CreateElectionRequest;
import esmukanov.evote_system.election_management.models.request.UpdateElectionRequest;
import esmukanov.evote_system.election_management.repositories.ElectionRepository;
import esmukanov.evote_system.election_management.services.ElectionOptionService;
import esmukanov.evote_system.election_management.services.ElectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ElectionServiceImpl implements ElectionService {

    private static final long MIN_ELECTION_OPTIONS = 2;

    private final ElectionRepository electionRepository;
    private final ElectionMapper electionMapper;

    private final ElectionOptionService electionOptionService;

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
                .startDateTime(LocalDateTime.now())
                .endDateTime(request.endDateTime())
                .createdAt(LocalDateTime.now())
                .status(ElectionStatus.DRAFT)
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

        if (existsElection.getStatus() != ElectionStatus.DRAFT) {
            throw new IllegalStateException("Редактировать можно только голосование в статусе DRAFT");
        }

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
    public void publishElection(String electionId) {
        Election existsElection = getElectionById(electionId);

        if (existsElection.getStatus() != ElectionStatus.DRAFT) {
            throw new IllegalStateException("Опубликовать можно только голосование в статусе DRAFT");
        }

        validateElectionBeforePublish(existsElection);

        existsElection.setStatus(ElectionStatus.SCHEDULED);
        electionRepository.save(electionMapper.toEntity(existsElection));
    }

    private void validateElectionBeforePublish(Election election) {
        if (election.getName() == null || election.getName().isBlank()) {
            throw new IllegalStateException("Название голосования обязательно");
        }

        if (election.getStartDateTime() == null) {
            throw new IllegalStateException("Дата начала голосования обязательна");
        }

        if (election.getEndDateTime() == null) {
            throw new IllegalStateException("Дата окончания голосования обязательна");
        }

        if (!election.getStartDateTime().isBefore(election.getEndDateTime())) {
            throw new IllegalStateException("Дата начала должна быть раньше даты окончания");
        }

        if (!election.getStartDateTime().isAfter(LocalDateTime.now())) {
            throw new IllegalStateException("Дата начала голосования должна быть в будущем");
        }

        long optionsCount = electionOptionService.countByElectionId(election.getId().toString());
        if (optionsCount < MIN_ELECTION_OPTIONS) {
            throw new IllegalStateException("Для публикации голосования нужно добавить минимум 2 варианта ответа");
        }
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
    public List<UUID> finishExpiredElections(LocalDateTime now) {
        List<UUID> electionIds = electionRepository.findIdsForFinishing(
                now,
                ElectionStatus.ACTIVE
        );

        List<UUID> completedElectionIds = new ArrayList<>();

        for (UUID electionId : electionIds) {
            int updated = electionRepository.completeElectionById(
                    electionId,
                    ElectionStatus.ACTIVE,
                    ElectionStatus.COMPLETED
            );

            if (updated > 0) {
                completedElectionIds.add(electionId);
            }
        }

        return completedElectionIds;
    }
}
