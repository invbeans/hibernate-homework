package ru.rassafel.hibernate.dao;

import java.util.Optional;
import ru.rassafel.hibernate.model.persistence.Article;
import ru.rassafel.hibernate.model.persistence.Reader;

public interface ReaderDao extends GenericDao<Reader, Long> {
    Optional<Reader> addArticle(Long id, Article article);
}
