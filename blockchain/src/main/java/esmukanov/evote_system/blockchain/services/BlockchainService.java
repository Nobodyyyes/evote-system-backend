package esmukanov.evote_system.blockchain.services;

import esmukanov.evote_system.blockchain.models.BlockchainRecord;
import esmukanov.evote_system.commons.enums.BlockchainEventType;

import java.util.List;
import java.util.UUID;

public interface BlockchainService {

    BlockchainRecord fixData(UUID relatedObjectId, String dataHash, BlockchainEventType eventType);

    BlockchainRecord verifyData(UUID relatedObjectId, String currentHash, BlockchainEventType eventType);

    List<BlockchainRecord> getRecordsByRelatedObjectId(UUID relatedObjectId);
}
