package ru.clevertec.ecl.knyazev.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clevertec.ecl.knyazev.entity.type.PersonHouseType;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HouseHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false)
    private Person person;

    @ManyToOne
    @JoinColumn(name = "house_id", referencedColumnName = "id", nullable = false)
    private House house;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PersonHouseType type;

    @Column
    private LocalDateTime date;
}
