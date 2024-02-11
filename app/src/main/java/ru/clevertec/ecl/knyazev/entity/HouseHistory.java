package ru.clevertec.ecl.knyazev.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedEntityGraphs;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clevertec.ecl.knyazev.entity.type.PersonHouseType;

import java.time.LocalDateTime;

@Entity
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "HouseHistory.person.passport",
                attributeNodes = {
                        @NamedAttributeNode(value = "person", subgraph = "Person.passport"),
                },
                subgraphs = {
                        @NamedSubgraph(
                                name = "Person.passport",
                                attributeNodes = {
                                        @NamedAttributeNode(value = "passport")
                                }
                        )
                }
        ),
        @NamedEntityGraph(
                name = "HouseHistory.house.address",
                attributeNodes = {
                        @NamedAttributeNode(value = "house", subgraph = "House.address"),
                },
                subgraphs = {
                        @NamedSubgraph(
                                name = "House.address",
                                attributeNodes = {
                                        @NamedAttributeNode(value = "address")
                                })
                }
        )
})
@Table(name = "persons_houses_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HouseHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false)
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id", referencedColumnName = "id", nullable = false)
    private House house;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PersonHouseType type;

    @Column
    private LocalDateTime date;
}
