package esmukanov.evote_system.election_management.services.impl;

import esmukanov.evote_system.commons.enums.ElectionStatus;
import esmukanov.evote_system.commons.mappers.ElectionMapper;
import esmukanov.evote_system.commons.models.Election;
import esmukanov.evote_system.election_management.exceptions.ElectionAlreadyExistsException;
import esmukanov.evote_system.election_management.exceptions.ElectionNotFoundException;
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
                .orElseThrow(() -> new ElectionNotFoundException("Election by ID [%s] not found".formatted(electionId)));
    }

    @Override
    public Election createElection(Election election) {
        if (electionRepository.existsById(election.getUuid())) {
            throw new ElectionAlreadyExistsException("Election by ID [%s] already exists".formatted(election.getUuid()));
        }

        return electionMapper.toModel(electionRepository.save(electionMapper.toEntity(election)));
    }

    @Override
    public Election updateElection(Election election) {
        if (election.getUuid() == null) {
            throw new IllegalArgumentException("Election id must not be null");
        }

        Election existsElection = getElectionById(election.getUuid().toString());
        existsElection.setName(election.getName());
        existsElection.setDescription(election.getDescription());
        existsElection.setStartDateTime(election.getStartDateTime());
        existsElection.setEndDateTime(election.getEndDateTime());
        existsElection.setCreatedAt(election.getCreatedAt());
        existsElection.setElectionStatus(election.getElectionStatus());
        existsElection.setCreatorInfo(election.getCreatorInfo());
        existsElection.setAccessElectionType(election.getAccessElectionType());

        return electionMapper.toModel(electionRepository.save(electionMapper.toEntity(existsElection)));
    }

    @Override
    public void deleteElection(String electionId) {
        electionRepository.deleteById(UUID.fromString(electionId));
    }

    @Override
    @Transactional
    public int activateElection(LocalDateTime activateTime) {
        return electionRepository.activateScheduledElections(
                activateTime,
                ElectionStatus.SCHEDULED,
                ElectionStatus.ACTIVE
        );
    }
}
