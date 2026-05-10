package esmukanov.evote_system.election_management.models;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ElectionOptionResult {

    UUID id;
    UUID optionId;
    String optionText;
    Integer orderNumber;
    Long votesCount;
    BigDecimal percentage;
}
