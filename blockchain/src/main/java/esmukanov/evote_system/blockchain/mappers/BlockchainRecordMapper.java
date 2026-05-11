package esmukanov.evote_system.blockchain.mappers;

import esmukanov.evote_system.blockchain.models.BlockchainRecord;
import esmukanov.evote_system.commons.entities.BlockchainRecordEntity;
import esmukanov.evote_system.commons.mappers.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BlockchainRecordMapper implements ModelMapper<BlockchainRecord, BlockchainRecordEntity> {

    @Override
    public BlockchainRecord toModel(BlockchainRecordEntity entity) {
        if (entity == null) {
            return null;
        }

        return BlockchainRecord.builder()
                .id(entity.getId())
                .relatedObjectId(entity.getRelatedObjectId())
                .dataHash(entity.getDataHash())
                .eventType(entity.getEventType())
                .status(entity.getStatus())
                .transactionId(entity.getTransactionId())
                .fixedAt(entity.getFixedAt())
                .verifiedAt(entity.getVerifiedAt())
                .build();
    }

    @Override
    public BlockchainRecordEntity toEntity(BlockchainRecord model) {
        if (model == null) {
            return null;
        }

        return BlockchainRecordEntity.builder()
                .id(model.getId())
                .relatedObjectId(model.getRelatedObjectId())
                .dataHash(model.getDataHash())
                .eventType(model.getEventType())
                .status(model.getStatus())
                .transactionId(model.getTransactionId())
                .fixedAt(model.getFixedAt())
                .verifiedAt(model.getVerifiedAt())
                .build();
    }
}
