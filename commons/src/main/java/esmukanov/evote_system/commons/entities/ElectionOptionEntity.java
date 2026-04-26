package esmukanov.evote_system.commons.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "ELECTION_OPTIONS")
public class ElectionOptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID uuid;

    @Column(name = "ELECTION_ID")
    UUID electionId;

    @Column(name = "TEXT")
    String optionText;

    @Column(name = "ORDER_NUMBER")
    Integer orderNumber;
}
