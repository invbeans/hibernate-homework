package ru.rassafel.hibernate.model.persistence;

import lombok.*;

import javax.persistence.*;

/**
 * @author rassafel
 */
@Entity
@Table(name = "address")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "address", length = 63, nullable = false)
    private String address;
}
