package ru.clevertec.ecl.knyazev.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID uuid;

    @Column(nullable = false, length = 15)
    private String name;

    @Column(nullable = false, length = 15)
    private String surname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @OneToOne
    @JoinColumn(name = "passport_id", referencedColumnName = "id", nullable = false)
    private Passport passport;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id", referencedColumnName = "id", nullable = false)
    private House livingHouse;

    @ManyToMany
    @JoinTable(name = "persons_houses_possessing",
            joinColumns = {@JoinColumn(name = "person_id")},
            inverseJoinColumns = {@JoinColumn(name = "house_id")})
    private List<House> possessedHouses;

    @Column(name = "create_date", updatable = false, nullable = false)
    private LocalDateTime createDate;

    @Column(name = "update_date", insertable = false)
    private LocalDateTime updateDate;

    public static enum Sex {
        male, female
    }
}
