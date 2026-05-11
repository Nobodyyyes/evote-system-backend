package esmukanov.evote_system.commons.entities;

import esmukanov.evote_system.commons.enums.BlockchainEventType;
import esmukanov.evote_system.commons.enums.BlockchainRecordStatus;
import jakarta.persistence.*;
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
@Entity
@Table(name = "BLOCKCHAIN_RECORDS")
public class BlockchainRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "RELATED_OBJECT_ID", nullable = false)
    UUID relatedObjectId;

    @Column(name = "DATA_HASH", nullable = false)
    String dataHash;

    @Column(name = "EVENT_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    BlockchainEventType eventType;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    BlockchainRecordStatus status;

    @Column(name = "TRANSACTION_ID", nullable = false)
    String transactionId;

    @Column(name = "FIXED_AT", nullable = false)
    LocalDateTime fixedAt;

    @Column(name = "VERIFIED_AT")
    LocalDateTime verifiedAt;
}
