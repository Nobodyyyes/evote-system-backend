package esmukanov.evote_system.user_management.repositories;

import esmukanov.evote_system.commons.entities.UserEntity;
import esmukanov.evote_system.commons.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    boolean existsByUsername(String username);

    List<UserEntity> findAllByUserStatus(UserStatus userStatus);
}
