package esmukanov.evote_system.election_management.services.impl;

import esmukanov.evote_system.commons.enums.ElectionStatus;
import esmukanov.evote_system.commons.mappers.ElectionMapper;
import esmukanov.evote_system.commons.mappers.ElectionOptionMapper;
import esmukanov.evote_system.commons.models.Election;
import esmukanov.evote_system.commons.models.ElectionOption;
import esmukanov.evote_system.election_management.exceptions.ElectionNotFoundException;
import esmukanov.evote_system.election_management.exceptions.ElectionOptionNotFoundException;
import esmukanov.evote_system.election_management.mappers.ElectionOptionResponseMapper;
import esmukanov.evote_system.election_management.models.reponse.ElectionOptionResponse;
import esmukanov.evote_system.election_management.models.request.CreateElectionOptionRequest;
import esmukanov.evote_system.election_management.models.request.UpdateElectionOptionRequest;
import esmukanov.evote_system.election_management.repositories.ElectionOptionRepository;
import esmukanov.evote_system.election_management.repositories.ElectionRepository;
import esmukanov.evote_system.election_management.services.ElectionOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ElectionOptionServiceImpl implements ElectionOptionService {

    private final ElectionRepository electionRepository;
    private final ElectionOptionRepository electionOptionRepository;

    private final ElectionMapper electionMapper;
    private final ElectionOptionMapper electionOptionMapper;

    private final ElectionOptionResponseMapper responseMapper;

    @Override
    public List<ElectionOptionResponse> getAllElectionOptionsByElectionId(String electionId) {
        return electionOptionMapper.toModels(electionOptionRepository.findAllByElectionId(UUID.fromString(electionId)))
                .stream()
                .map(responseMapper::toResponse)
                .toList();
    }

    @Override
    public String createElectionOption(String electionId, CreateElectionOptionRequest request) {
        Election existsElection = electionRepository.findById(UUID.fromString(electionId))
                .map(electionMapper::toModel)
                .orElseThrow(() -> new ElectionNotFoundException("Голосование по ID [%s] не найдено".formatted(electionId)));

        checkElectionEditable(existsElection);

        ElectionOption newElection = ElectionOption.builder()
                .electionId(UUID.fromString(electionId))
                .optionText(request.text())
                .orderNumber(request.orderNumber())
                .build();

        return electionOptionRepository.save(electionOptionMapper.toEntity(newElection)).getUuid().toString();
    }

    @Override
    public void updateElectionOption(String electionId, String electionOptionId, UpdateElectionOptionRequest request) {
        Election existsElection = electionRepository.findById(UUID.fromString(electionId))
                .map(electionMapper::toModel)
                .orElseThrow(() -> new ElectionNotFoundException("Голосование по ID [%s] не найдено".formatted(electionId)));

        checkElectionEditable(existsElection);

        ElectionOption electionOption = electionOptionRepository.findById(UUID.fromString(electionOptionId))
                .map(electionOptionMapper::toModel)
                .orElseThrow(() -> new ElectionOptionNotFoundException("Вариант ответа не относится к данному голосованию"));

        electionOption.setOptionText(request.text());
        electionOption.setOrderNumber(request.orderNumber());

        electionOptionRepository.save(electionOptionMapper.toEntity(electionOption));
    }

    @Override
    public void deleteElectionOption(String electionId, String electionOptionId) {
        Election existsElection = electionRepository.findById(UUID.fromString(electionId))
                .map(electionMapper::toModel)
                .orElseThrow(() -> new ElectionNotFoundException("Голосование по ID [%s] не найдено".formatted(electionId)));

        checkElectionEditable(existsElection);

        ElectionOption electionOption = electionOptionRepository.findById(UUID.fromString(electionOptionId))
                .map(electionOptionMapper::toModel)
                .orElseThrow(() -> new ElectionOptionNotFoundException("Вариант ответа не найден"));

        if (!electionOption.getElectionId().equals(UUID.fromString(electionId))) {
            throw new IllegalStateException("Вариант ответа не относится к данному голосованию");
        }

        electionOptionRepository.deleteById(electionOption.getUuid());
    }

    private void checkElectionEditable(Election election) {
        if (election.getElectionStatus() != ElectionStatus.DRAFT) {
            throw new IllegalStateException("Варианты ответа можно изменять только до публикации голосования");
        }
    }

    @Override
    public void deleteAllByElection(String electionId) {
        electionOptionRepository.deleteAllByElectionId(UUID.fromString(electionId));
    }
}
