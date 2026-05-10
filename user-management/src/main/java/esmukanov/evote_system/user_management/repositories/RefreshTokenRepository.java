package esmukanov.evote_system.user_management.repositories;

import esmukanov.evote_system.commons.entities.RefreshTokenEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

    @EntityGraph(attributePaths = {"user", "user.roles"})
    Optional<RefreshTokenEntity> findByTokenHashAndRevokedFalse(String tokenHash);
}
