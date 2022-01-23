package ru.rassafel.hibernate.model.persistence;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * @author rassafel
 */
@Entity
@Table(name = "student_group")
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", length = 63, nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "group")
    @ToString.Exclude
    private List<Student> students;
}
