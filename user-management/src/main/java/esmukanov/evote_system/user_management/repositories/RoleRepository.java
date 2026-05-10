package esmukanov.evote_system.user_management.repositories;

import esmukanov.evote_system.commons.entities.RoleEntity;
import esmukanov.evote_system.commons.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByRole(Role role);

    boolean existsByRole(Role role);
}
