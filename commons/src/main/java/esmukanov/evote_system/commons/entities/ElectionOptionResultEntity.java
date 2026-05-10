package esmukanov.evote_system.commons.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ELECTION_OPTION_RESULTS")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ElectionOptionResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "result_id", nullable = false)
    ElectionResultEntity result;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id", nullable = false)
    ElectionOptionEntity option;

    @Column(name = "OPTION_TEXT")
    String optionText;

    @Column(name = "ORDER_NUMBER")
    Integer orderNumber;

    @Column(name = "VOTES_COUNT")
    Long votesCount;

    @Column(name = "PERCENTAGE", precision = 5, scale = 2)
    BigDecimal percentage;
}
