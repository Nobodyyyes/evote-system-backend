package esmukanov.evote_system.election_management.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HexFormat;
import java.util.UUID;

@Service
public class VoteHashService {

    private final String voteSalt;

    public VoteHashService(@Value("${app.security.vote-salt}") String voteSalt) {
        this.voteSalt = voteSalt;
    }

    public String generateVoterHash(UUID userId, UUID electionId) {
        return sha256(userId + ":" + electionId + ":" + voteSalt);
    }

    public String generateVoteHash(UUID electionId,
                                   UUID optionId,
                                   String voterHash,
                                   LocalDateTime createdAt) {
        return sha256(electionId + ":" + optionId + ":" + voterHash + ":" + createdAt + ":" + voteSalt);
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
