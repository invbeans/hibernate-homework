package ru.rassafel.hibernate.model.persistence;

import java.time.LocalDate;
import java.util.List;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "article")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    
    @Column(name = "name", length = 50, nullable = false)
    private String name;
    
    @Column(name = "write_date")
    @EqualsAndHashCode.Include
    private LocalDate writeDate;
    
    //добавить автора
    @ManyToOne
    @JoinColumn(name = "author_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Author author;
    
    //добавить читателей
    //@OneToMany(mappedBy = "articles")
    @ManyToMany(mappedBy = "articles")
    @ToString.Exclude
    private List<Reader> readers;
}
