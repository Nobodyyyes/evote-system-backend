package esmukanov.evote_system.blockchain.repositories;

import esmukanov.evote_system.commons.entities.BlockchainRecordEntity;
import esmukanov.evote_system.commons.enums.BlockchainEventType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BlockchainRecordRepository extends JpaRepository<BlockchainRecordEntity, UUID> {

    Optional<BlockchainRecordEntity> findByRelatedObjectIdAndEventType(
            UUID relatedObjectId,
            BlockchainEventType eventType
    );

    List<BlockchainRecordEntity> findAllByRelatedObjectId(UUID relatedObjectId);

    List<BlockchainRecordEntity> findAllByEventType(BlockchainEventType eventType);

    boolean existsByRelatedObjectIdAndEventType(UUID relatedObjectId, BlockchainEventType eventType);
}
