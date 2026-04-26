package esmukanov.evote_system.commons.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ElectionOption {

    UUID uuid;
    UUID electionId;
    String optionText;
    Integer orderNumber;
}
