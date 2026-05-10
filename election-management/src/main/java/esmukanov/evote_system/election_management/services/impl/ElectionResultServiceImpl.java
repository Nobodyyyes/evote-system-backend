package esmukanov.evote_system.election_management.services.impl;

import esmukanov.evote_system.commons.entities.ElectionEntity;
import esmukanov.evote_system.commons.entities.ElectionOptionEntity;
import esmukanov.evote_system.commons.entities.ElectionResultEntity;
import esmukanov.evote_system.commons.enums.ElectionResultVisibilityType;
import esmukanov.evote_system.commons.enums.ElectionStatus;
import esmukanov.evote_system.election_management.exceptions.ElectionNotFoundException;
import esmukanov.evote_system.election_management.exceptions.ElectionResultCalculateException;
import esmukanov.evote_system.election_management.exceptions.ElectionResultNotAvailableException;
import esmukanov.evote_system.election_management.mappers.ElectionResultMapper;
import esmukanov.evote_system.election_management.models.Election;
import esmukanov.evote_system.election_management.models.ElectionOptionResult;
import esmukanov.evote_system.election_management.models.ElectionResult;
import esmukanov.evote_system.election_management.models.VoteCount;
import esmukanov.evote_system.election_management.repositories.ElectionOptionRepository;
import esmukanov.evote_system.election_management.repositories.ElectionRepository;
import esmukanov.evote_system.election_management.repositories.ElectionResultRepository;
import esmukanov.evote_system.election_management.repositories.VoteRepository;
import esmukanov.evote_system.election_management.services.ElectionResultService;
import esmukanov.evote_system.election_management.services.ElectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ElectionResultServiceImpl implements ElectionResultService {

    private final ElectionService electionService;

    private final ElectionRepository electionRepository;
    private final ElectionOptionRepository electionOptionRepository;
    private final ElectionResultRepository electionResultRepository;
    private final VoteRepository voteRepository;

    private final ElectionResultMapper electionResultMapper;

    @Override
    @Transactional
    public ElectionResult calculateResult(String electionId) {
        UUID electionUuid = UUID.fromString(electionId);

        Election election = electionService.getElectionById(electionId);

        if (election.getStatus() != ElectionStatus.COMPLETED) {
            throw new ElectionResultCalculateException(
                    "Нельзя подсчитать результаты незавершенного голосования"
            );
        }

        ElectionEntity electionEntity = electionRepository.findById(electionUuid)
                .orElseThrow(() -> new ElectionNotFoundException(
                        "Голосование с идентификатором [%s] не найдено".formatted(electionId)
                ));

        List<ElectionOptionEntity> options =
                electionOptionRepository.findAllByElectionId(electionUuid);

        if (options.isEmpty()) {
            throw new ElectionResultCalculateException(
                    "Нельзя подсчитать результаты голосования без вариантов ответа"
            );
        }

        List<VoteCount> voteCounts = voteRepository.countVotesByOption(electionUuid);

        Map<UUID, Long> votesByOptionId = voteCounts.stream()
                .collect(Collectors.toMap(
                        VoteCount::getOptionId,
                        VoteCount::getVotesCount
                ));

        long totalVotes = voteRepository.countByElectionId(electionUuid);

        List<ElectionOptionResult> optionResults = options.stream()
                .sorted(Comparator.comparing(ElectionOptionEntity::getOrderNumber))
                .map(option -> {
                    long votesCount = votesByOptionId.getOrDefault(option.getId(), 0L);

                    return ElectionOptionResult.builder()
                            .optionId(option.getId())
                            .optionText(option.getOptionText())
                            .orderNumber(option.getOrderNumber())
                            .votesCount(votesCount)
                            .percentage(calculatePercentage(votesCount, totalVotes))
                            .build();
                })
                .toList();

        ElectionResult result = ElectionResult.builder()
                .electionId(electionUuid)
                .electionTitle(election.getName())
                .totalVotes(totalVotes)
                .calculatedAt(LocalDateTime.now())
                .optionResults(optionResults)
                .build();

        electionResultRepository.findByElectionId(electionUuid)
                .ifPresent(existingResult -> {
                    electionResultRepository.delete(existingResult);
                    electionResultRepository.flush();
                });

        ElectionResultEntity resultEntity =
                electionResultMapper.toEntity(result, electionEntity, options);

        ElectionResultEntity savedResult = electionResultRepository.save(resultEntity);

        return electionResultMapper.toModel(savedResult);
    }

    @Override
    @Transactional(readOnly = true)
    public ElectionResult getResult(String electionId) {
        UUID electionUuid = UUID.fromString(electionId);

        Election election = electionService.getElectionById(electionId);

        if (!canShowResult(election)) {
            throw new ElectionResultNotAvailableException(
                    "Результаты голосования пока недоступны"
            );
        }

        ElectionResultEntity result = electionResultRepository.findFullByElectionId(electionUuid)
                .orElseThrow(() -> new ElectionResultNotAvailableException(
                        "Результаты голосования еще не рассчитаны"
                ));

        return electionResultMapper.toModel(result);
    }

    @Override
    @Transactional
    public ElectionResult publishResult(String electionId) {
        UUID electionUuid = UUID.fromString(electionId);

        ElectionEntity electionEntity = electionRepository.findById(electionUuid)
                .orElseThrow(() -> new ElectionNotFoundException(
                        "Голосование с идентификатором [%s] не найдено".formatted(electionId)
                ));

        if (electionEntity.getElectionStatus() != ElectionStatus.COMPLETED) {
            throw new ElectionResultCalculateException(
                    "Нельзя публиковать результаты незавершенного голосования"
            );
        }

        if (!electionResultRepository.existsByElectionId(electionUuid)) {
            calculateResult(electionId);
        }

        electionEntity.setResultPublished(true);
        electionRepository.save(electionEntity);

        ElectionResultEntity result = electionResultRepository.findFullByElectionId(electionUuid)
                .orElseThrow(() -> new ElectionResultNotAvailableException(
                        "Результаты голосования еще не рассчитаны"
                ));

        return electionResultMapper.toModel(result);
    }

    private boolean canShowResult(Election election) {
        if (election.getStatus() != ElectionStatus.COMPLETED) {
            return false;
        }

        if (election.getResultVisibilityType() == ElectionResultVisibilityType.AFTER_FINISH) {
            return true;
        }

        if (election.getResultVisibilityType() == ElectionResultVisibilityType.AFTER_PUBLISH) {
            return election.isResultPublished();
        }

        return false;
    }

    private BigDecimal calculatePercentage(long votesCount, long totalVotes) {
        if (totalVotes == 0) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }

        return BigDecimal.valueOf(votesCount)
                .multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(totalVotes), 2, RoundingMode.HALF_UP);
    }
}
