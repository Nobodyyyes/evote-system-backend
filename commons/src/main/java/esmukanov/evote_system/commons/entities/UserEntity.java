package esmukanov.evote_system.commons.entities;

import esmukanov.evote_system.commons.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
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

    @Column(name = "FIRSTNAME", nullable = false)
    String firstname;

    @Column(name = "NAME", nullable = false)
    String name;

    @Column(name = "EMAIL")
    String email;

    @Column(name = "USERNAME", nullable = false, unique = true)
    String username;

    @Column(name = "PASSWORD", nullable = false)
    String password;

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "USER_ROLES",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    Set<RoleEntity> roles = new HashSet<>();

    @Column(name = "USER_STATUS")
    @Enumerated(EnumType.STRING)
    UserStatus status;

    @Column(name = "CREATED_DATE")
    LocalDateTime createdDate;
}
