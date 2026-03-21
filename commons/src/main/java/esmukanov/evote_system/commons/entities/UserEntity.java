package esmukanov.evote_system.commons.entities;

import esmukanov.evote_system.commons.enums.Role;
import esmukanov.evote_system.commons.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "USERS")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "USERNAME")
    String username;

    @Column(name = "EMAIL")
    String email;

    @Column(name = "PASSWORD")
    String password;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    Role role;

    @Column(name = "USER_STATUS")
    @Enumerated(EnumType.STRING)
    UserStatus userStatus;

    @Column(name = "CREATED_DATE")
    LocalDateTime createdDate;
}
