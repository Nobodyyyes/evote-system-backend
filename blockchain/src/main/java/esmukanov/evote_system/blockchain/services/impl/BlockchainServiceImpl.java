package esmukanov.evote_system.blockchain.services.impl;

import esmukanov.evote_system.blockchain.exceptions.BlockchainRecordNotFoundException;
import esmukanov.evote_system.blockchain.exceptions.BlockchainVerificationException;
import esmukanov.evote_system.blockchain.mappers.BlockchainRecordMapper;
import esmukanov.evote_system.blockchain.models.BlockchainRecord;
import esmukanov.evote_system.blockchain.repositories.BlockchainRecordRepository;
import esmukanov.evote_system.blockchain.services.BlockchainService;
import esmukanov.evote_system.commons.entities.BlockchainRecordEntity;
import esmukanov.evote_system.commons.enums.BlockchainEventType;
import esmukanov.evote_system.commons.enums.BlockchainRecordStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BlockchainServiceImpl implements BlockchainService {

    private final BlockchainRecordRepository blockchainRecordRepository;
    private final BlockchainRecordMapper blockchainRecordMapper;

    @Override
    @Transactional
    public BlockchainRecord fixData(UUID relatedObjectId,
                                    String dataHash,
                                    BlockchainEventType eventType) {
        if (blockchainRecordRepository.existsByRelatedObjectIdAndEventType(relatedObjectId, eventType)) {
            return blockchainRecordRepository
                    .findByRelatedObjectIdAndEventType(relatedObjectId, eventType)
                    .map(blockchainRecordMapper::toModel)
                    .orElseThrow(() -> new BlockchainRecordNotFoundException(
                            "Блокчейн-запись не найдена"
                    ));
        }

        String transactionId = generateTransactionId(eventType, relatedObjectId);

        BlockchainRecordEntity record = BlockchainRecordEntity.builder()
                .relatedObjectId(relatedObjectId)
                .dataHash(dataHash)
                .eventType(eventType)
                .status(BlockchainRecordStatus.CONFIRMED)
                .transactionId(transactionId)
                .fixedAt(LocalDateTime.now())
                .build();

        BlockchainRecordEntity savedRecord = blockchainRecordRepository.save(record);

        return blockchainRecordMapper.toModel(savedRecord);
    }

    @Override
    @Transactional
    public BlockchainRecord verifyData(UUID relatedObjectId,
                                       String currentHash,
                                       BlockchainEventType eventType) {
        BlockchainRecordEntity record = blockchainRecordRepository
                .findByRelatedObjectIdAndEventType(relatedObjectId, eventType)
                .orElseThrow(() -> new BlockchainRecordNotFoundException(
                        "Блокчейн-запись для проверки не найдена"
                ));

        if (!record.getDataHash().equals(currentHash)) {
            throw new BlockchainVerificationException(
                    "Проверка целостности не пройдена. Текущий хэш не совпадает с зафиксированным"
            );
        }

        record.setVerifiedAt(LocalDateTime.now());

        return blockchainRecordMapper.toModel(record);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BlockchainRecord> getRecordsByRelatedObjectId(UUID relatedObjectId) {
        return blockchainRecordMapper.toModels(
                blockchainRecordRepository.findAllByRelatedObjectId(relatedObjectId)
        );
    }

    private String generateTransactionId(BlockchainEventType eventType, UUID relatedObjectId) {
        return "tx-" + eventType.name().toLowerCase() + "-" + relatedObjectId + "-" + UUID.randomUUID();
    }
}
