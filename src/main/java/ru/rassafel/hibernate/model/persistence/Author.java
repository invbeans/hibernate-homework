package ru.rassafel.hibernate.model.persistence;

import java.time.LocalDate;
import lombok.*;
import java.util.List;

import javax.persistence.*;

/**
 * @author rassafel
 */
@Entity
@Table(name = "author")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    
    @Column(name = "first_name", length = 30, nullable = false)
    private String firstName;
    
    @Column(name = "last_name", length = 30, nullable = false)
    private String lastName;
    
    @Column(name = "birthday")
    @EqualsAndHashCode.Include
    private LocalDate birthday;
    
    //добавить статьи
    @OneToMany(mappedBy = "author", orphanRemoval = true)
    @ToString.Exclude
    private List<Article> articles;
}
