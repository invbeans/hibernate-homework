package ru.rassafel.hibernate.dao;

import ru.rassafel.hibernate.model.persistence.Author;
import java.util.List;
import java.util.Optional;

public interface AuthorDao extends GenericDao<Author, Long> {
    List<Author> findByMinArticlesAmount(int amount);
    
}
