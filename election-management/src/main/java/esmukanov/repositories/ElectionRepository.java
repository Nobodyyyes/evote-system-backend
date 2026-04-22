package esmukanov.repositories;

import esmukanov.evote_system.commons.entities.ElectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ElectionRepository extends JpaRepository<ElectionEntity, UUID> {
}
