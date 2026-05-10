package esmukanov.evote_system.election_management.repositories;

import esmukanov.evote_system.commons.entities.ElectionResultEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ElectionResultRepository extends JpaRepository<ElectionResultEntity, UUID> {

    Optional<ElectionResultEntity> findByElectionId(UUID electionId);

    boolean existsByElectionId(UUID electionId);

    void deleteByElectionId(UUID electionId);

    @EntityGraph(attributePaths = {"election", "optionResults", "optionResults.option"})
    @Query("""
            select r
            from ElectionResultEntity r
            where r.election.id = :electionId
            """)
    Optional<ElectionResultEntity> findFullByElectionId(@Param("electionId") UUID electionId);
}
