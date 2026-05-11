package esmukanov.evote_system.blockchain.models;

import esmukanov.evote_system.commons.enums.BlockchainEventType;
import esmukanov.evote_system.commons.enums.BlockchainRecordStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlockchainRecord {

    UUID id;
    UUID relatedObjectId;
    String dataHash;
    BlockchainEventType eventType;
    BlockchainRecordStatus status;
    String transactionId;
    LocalDateTime fixedAt;
    LocalDateTime verifiedAt;
}
