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
    UUID uuid;

    @Column(name = "DATA_HASH")
    String dataHash;

    @Column(name = "RECORDED_DATE")
    LocalDateTime recordedDate;

    @Column(name = "BLOCKCHAIN_EVENT_TYPE")
    @Enumerated(EnumType.STRING)
    BlockchainEventType blockchainEventType;

    @Column(name = "BLOCKCHAIN_RECORD_STATUS")
    @Enumerated(EnumType.STRING)
    BlockchainRecordStatus blockchainRecordStatus;
}
