package esmukanov.evote_system.user_management.repositories;

import esmukanov.evote_system.commons.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
}
