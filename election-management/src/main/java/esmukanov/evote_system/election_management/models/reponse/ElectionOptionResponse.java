package esmukanov.evote_system.election_management.models.reponse;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ElectionOptionResponse {

    String electionOptionId;
    String text;
    Integer orderNumber;
}
