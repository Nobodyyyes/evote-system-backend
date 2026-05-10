package esmukanov.evote_system.commons.models;

import esmukanov.evote_system.commons.entities.RoleEntity;
import esmukanov.evote_system.commons.enums.Role;
import esmukanov.evote_system.commons.enums.UserStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    UUID id;
    String username;
    String email;
    String password;
    Set<RoleEntity> roles;
    UserStatus userStatus;
    LocalDateTime createdDate;
}
