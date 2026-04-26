package esmukanov.evote_system.election_management.repositories;

import esmukanov.evote_system.commons.entities.ElectionEntity;
import esmukanov.evote_system.commons.enums.ElectionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ElectionRepository extends JpaRepository<ElectionEntity, UUID> {

    @Modifying
    @Query("""
            update ElectionEntity e
            set e.electionStatus = :activeStatus
            where e.electionStatus = :scheduledStatus
               and e.startDateTime <= :now
               and e.endDateTime > :now
            """)
    int activateScheduledElections(
            @Param("now") LocalDateTime now,
            @Param("scheduledStatus") ElectionStatus scheduledStatus,
            @Param("activeStatus") ElectionStatus activeStatus
    );

    @Modifying
    @Query("""
            update ElectionEntity e
            set e.electionStatus = :finishedStatus
            where e.electionStatus = :activeStatus
            and e.electionStatus <= :now
            """)
    int finishActiveElections(
            @Param("now") LocalDateTime now,
            @Param("activeStatus") ElectionStatus activeStatus,
            @Param("finishedStatus") ElectionStatus finishedStatus
    );
}
