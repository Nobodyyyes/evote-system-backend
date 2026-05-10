package esmukanov.evote_system.election_management.repositories;

import esmukanov.evote_system.commons.entities.VoteEntity;
import esmukanov.evote_system.election_management.models.VoteCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface VoteRepository extends JpaRepository<VoteEntity, UUID> {

    boolean existsByElectionIdAndVoterHash(UUID electionId, String voterHash);

    long countByElectionId(UUID electionId);

    @Query("""
            SELECT new esmukanov.evote_system.election_management.models.VoteCount(
                v.option.id,
                COUNT(v.id)
            )
            FROM VoteEntity v
            WHERE v.election.id = :electionId
            GROUP BY v.option.id
            """)
    List<VoteCount> countVotesByOption(@Param("electionId") UUID electionId);
}
