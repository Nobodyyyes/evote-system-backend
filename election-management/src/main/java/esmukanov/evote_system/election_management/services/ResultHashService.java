package esmukanov.evote_system.election_management.services;

import esmukanov.evote_system.election_management.models.ElectionOptionResult;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.HexFormat;
import java.util.List;
import java.util.UUID;

@Service
public class ResultHashService {

    public String generateResultHash(UUID electionId,
                                     long totalVotes,
                                     List<ElectionOptionResult> optionResults) {
        StringBuilder payload = new StringBuilder();

        payload.append("electionId=").append(electionId).append("|");
        payload.append("totalVotes=").append(totalVotes).append("|");

        optionResults.stream()
                .sorted(Comparator.comparing(ElectionOptionResult::getOrderNumber))
                .forEach(optionResult -> payload
                        .append("optionId=").append(optionResult.getOptionId()).append(";")
                        .append("votes=").append(optionResult.getVotesCount()).append(";")
                        .append("percentage=").append(optionResult.getPercentage()).append("|")
                );

        return sha256(payload.toString());
    }

    private String sha256(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(value.getBytes(StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm is not available", e);
        }
    }
}
