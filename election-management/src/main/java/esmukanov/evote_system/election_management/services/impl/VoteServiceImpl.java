package esmukanov.evote_system.election_management.services.impl;

import esmukanov.evote_system.blockchain.services.BlockchainService;
import esmukanov.evote_system.commons.entities.ElectionEntity;
import esmukanov.evote_system.commons.entities.ElectionOptionEntity;
import esmukanov.evote_system.commons.entities.VoteEntity;
import esmukanov.evote_system.commons.enums.AccessElectionType;
import esmukanov.evote_system.commons.enums.BlockchainEventType;
import esmukanov.evote_system.commons.enums.ElectionStatus;
import esmukanov.evote_system.election_management.exceptions.ElectionNotFoundException;
import esmukanov.evote_system.election_management.exceptions.ElectionOptionNotFoundException;
import esmukanov.evote_system.election_management.exceptions.VoteAlreadyExistsException;
import esmukanov.evote_system.election_management.exceptions.VoteNotAllowedException;
import esmukanov.evote_system.election_management.mappers.VoteMapper;
import esmukanov.evote_system.election_management.models.Vote;
import esmukanov.evote_system.election_management.repositories.ElectionOptionRepository;
import esmukanov.evote_system.election_management.repositories.ElectionRepository;
import esmukanov.evote_system.election_management.repositories.VoteRepository;
import esmukanov.evote_system.election_management.services.ParticipationService;
import esmukanov.evote_system.election_management.services.VoteHashService;
import esmukanov.evote_system.election_management.services.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final ElectionRepository electionRepository;
    private final ElectionOptionRepository electionOptionRepository;
    private final VoteRepository voteRepository;
    private final ParticipationService participationService;
    private final BlockchainService blockchainService;

    private final VoteMapper voteMapper;
    private final VoteHashService voteHashService;

    @Override
    @Transactional
    public Vote castVote(String electionId, String optionId, String userId) {
        UUID electionUuid = parseUuid(electionId, "Некорректный идентификатор голосования");
        UUID optionUuid = parseUuid(optionId, "Некорректный идентификатор варианта ответа");
        UUID userUuid = parseUuid(userId, "Некорректный идентификатор пользователя");

        ElectionEntity election = electionRepository.findById(electionUuid)
                .orElseThrow(() -> new ElectionNotFoundException(
                        "Голосование с идентификатором [%s] не найдено".formatted(electionId)
                ));

        checkElectionIsActive(election);

        ElectionOptionEntity option = electionOptionRepository.findById(optionUuid)
                .orElseThrow(() -> new ElectionOptionNotFoundException(
                        "Вариант ответа с идентификатором [%s] не найден".formatted(optionId)
                ));

        checkOptionBelongsToElection(option, electionUuid);

        checkUserCanVote(election, userUuid);

        String voterHash = voteHashService.generateVoterHash(userUuid, electionUuid);

        checkUserHasNotVoted(electionUuid, userUuid, voterHash);

        LocalDateTime now = LocalDateTime.now();

        String voteHash = voteHashService.generateVoteHash(
                electionUuid,
                optionUuid,
                voterHash,
                now
        );

        Vote vote = Vote.builder()
                .electionId(electionUuid)
                .optionId(optionUuid)
                .voterHash(voterHash)
                .voteHash(voteHash)
                .createdAt(now)
                .build();

        VoteEntity voteEntity = voteMapper.toEntity(vote, election, option);

        try {
            VoteEntity savedVote = voteRepository.save(voteEntity);

            participationService.saveParticipation(
                    userUuid,
                    electionUuid,
                    now
            );

            blockchainService.fixData(savedVote.getId(), savedVote.getVoteHash(), BlockchainEventType.VOTE_CAST);

            return voteMapper.toModel(savedVote);
        } catch (DataIntegrityViolationException exception) {
            throw new VoteAlreadyExistsException(
                    "Пользователь уже проголосовал в данном голосовании"
            );
        }
    }

    private void checkElectionIsActive(ElectionEntity election) {
        if (election.getElectionStatus() != ElectionStatus.ACTIVE) {
            throw new VoteNotAllowedException(
                    "Голосование не активно. Отправка голоса невозможна"
            );
        }
    }

    private void checkOptionBelongsToElection(ElectionOptionEntity option, UUID electionId) {
        if (!electionId.equals(option.getElectionId())) {
            throw new VoteNotAllowedException(
                    "Выбранный вариант ответа не относится к данному голосованию"
            );
        }
    }

    private void checkUserCanVote(ElectionEntity election, UUID userId) {
        if (election.getAccessElectionType() == AccessElectionType.ALL_AUTHORIZED_USERS) {
            return;
        }

        if (election.getAccessElectionType() == AccessElectionType.SELECTED_USERS_ONLY) {
            throw new VoteNotAllowedException(
                    "Для голосований с ограниченным доступом список участников еще не реализован"
            );
        }

        throw new VoteNotAllowedException(
                "Неизвестный тип доступа к голосованию"
        );
    }

    private void checkUserHasNotVoted(UUID electionId, UUID userId, String voterHash) {
        if (participationService.hasParticipated(userId, electionId)) {
            throw new VoteAlreadyExistsException(
                    "Пользователь уже участвовал в данном голосовании"
            );
        }

        boolean alreadyVoted = voteRepository.existsByElectionIdAndVoterHash(
                electionId,
                voterHash
        );

        if (alreadyVoted) {
            throw new VoteAlreadyExistsException(
                    "Пользователь уже проголосовал в данном голосовании"
            );
        }
    }

    private UUID parseUuid(String value, String errorMessage) {
        try {
            return UUID.fromString(value);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
