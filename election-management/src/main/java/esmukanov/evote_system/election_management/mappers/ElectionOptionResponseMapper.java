package esmukanov.evote_system.election_management.mappers;

import esmukanov.evote_system.commons.models.ElectionOption;
import esmukanov.evote_system.election_management.models.reponse.ElectionOptionResponse;
import org.springframework.stereotype.Component;

@Component
public class ElectionOptionResponseMapper {

    public ElectionOptionResponse toResponse(ElectionOption electionOption) {
        return ElectionOptionResponse.builder()
                .electionOptionId(electionOption.getUuid().toString())
                .text(electionOption.getOptionText())
                .orderNumber(electionOption.getOrderNumber())
                .build();
    }
}
