package esmukanov.evote_system.election_management.repositories;

import esmukanov.evote_system.commons.entities.ParticipationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParticipationRepository extends JpaRepository<ParticipationEntity, UUID> {

    boolean existsByUserIdAndElectionId(UUID userId, UUID electionId);

    Optional<ParticipationEntity> findByUserIdAndElectionId(UUID userId, UUID electionId);

    List<ParticipationEntity> findAllByElectionId(UUID electionId);

    long countByElectionIdAndHasVotedTrue(UUID electionId);
}
