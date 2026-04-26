package esmukanov.evote_system.election_management.repositories;

import esmukanov.evote_system.commons.entities.ElectionOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ElectionOptionRepository extends JpaRepository<ElectionOptionEntity, UUID> {

    List<ElectionOptionEntity> findAllByElectionId(UUID electionId);

    void deleteAllByElectionId(UUID electionId);

    long countAllByElectionId(UUID electionId);
}
