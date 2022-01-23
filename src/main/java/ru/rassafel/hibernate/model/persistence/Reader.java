package ru.rassafel.hibernate.model.persistence;

import java.time.LocalDate;
import lombok.*;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "reader")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    
    @Column(name = "first_name", length = 30, nullable = false)
    private String firstName;
    
    @Column(name = "last_name", length = 30, nullable = false)
    private String lastName;
    
    @Column(name = "email", length = 30)
    private String email;
    
    //добавить статьи
    //@OneToMany(mappedBy = "readers")
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "reader_article",
            joinColumns = { @JoinColumn(name = "reader_id") },
            inverseJoinColumns = { @JoinColumn(name = "article_id") }
    )
    @ToString.Exclude
    private List<Article> articles;
}
