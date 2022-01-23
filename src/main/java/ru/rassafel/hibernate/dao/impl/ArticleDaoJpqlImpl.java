package ru.rassafel.hibernate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.rassafel.hibernate.dao.ArticleDao;
//import ru.rassafel.hibernate.dao.impl.ReaderDaoJpqlImpl;
import ru.rassafel.hibernate.model.persistence.Article;
import ru.rassafel.hibernate.util.SessionUtil;

import javax.persistence.NoResultException;
import java.util.List;
import ru.rassafel.hibernate.model.persistence.Author;
import java.util.Optional;

@RequiredArgsConstructor
public class ArticleDaoJpqlImpl implements ArticleDao {
    //private static AuthorDao autDao = new AuthorDaoJpqlImpl();
    //private static ReaderDao reaDao = new ReaderDaoJpqlImpl();
    
    @Override
    public List<Article> findByAuthorSecondNamePrefix(String prefix) {
        try (Session session = SessionUtil.createSession()){
            return session
                    .createQuery("select a " +
                            "from Article a " +
                            "where a.author.last_name like ?1", Article.class)
                    .setParameter(1, prefix + "%")
                    .getResultList();
        }
    }
    
    @Override
    public Optional<Article> findById(Long id) {
        try (Session session = SessionUtil.createSession()) {
            Article article = session
                .createQuery("from Student u " +
                    "where u.id = ?1", Article.class)
                .setParameter(1, id)
                .getSingleResult();
            return Optional.of(article);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }
    
    @Override
    public List<Article> findAll() {
        try (Session session = SessionUtil.createSession()) {
            return session
                .createQuery("from Student", Article.class)
                .getResultList();
        }
    }
    
    @Override
    public Article save(Article article) {
        Author author = article.getAuthor();      
        //сравнение возрастов
        if(article.getWriteDate().isBefore(author.getBirthday())){
            return null;
        }
        
        try (Session session = SessionUtil.createSession()) {
            Transaction transaction = session.beginTransaction();
            //возможно, просто возможно что авторы/читатели будут двоиться
            //а взмж при сохранении у них закрепятся id и они будут
            //или не будут переписываться

            article.getReaders().forEach(rea -> session.saveOrUpdate(rea));
            session.saveOrUpdate(article.getAuthor());
            
            session.save(article);

            transaction.commit();
            return article;
        }
    }
    
    @Override
    public Optional<Article> update(Long id, Article article) {
        Optional<Article> foundById = findById(id);
        if (!foundById.isPresent()) {
            System.out.println("Student with id = " + id + " does not exists");
            return foundById;
        }

        try (Session session = SessionUtil.createSession()) {
            Transaction transaction = session.beginTransaction();

            article.setId(id);
            session.update(article);

            transaction.commit();
            return Optional.of(article);
        }
    }
    
    @Override
    public Optional<Article> deleteById(Long id) {
        Optional<Article> foundById = findById(id);
        if (!foundById.isPresent()) {
            System.out.println("Student with id = " + id + " does not exists");
            return foundById;
        }
        Article article = foundById.get();


        try (Session session = SessionUtil.createSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(article);
            transaction.commit();
            return foundById;
        }
    }
}
