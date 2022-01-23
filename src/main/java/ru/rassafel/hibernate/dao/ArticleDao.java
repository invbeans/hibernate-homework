package ru.rassafel.hibernate.dao;

import ru.rassafel.hibernate.model.persistence.Article;
import java.util.List;
import java.util.Optional;

public interface ArticleDao extends GenericDao<Article, Long> {
    List<Article> findByAuthorSecondNamePrefix(String prefix);
    
}
