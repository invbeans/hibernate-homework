package ru.rassafel.hibernate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.rassafel.hibernate.dao.ReaderDao;
import ru.rassafel.hibernate.model.persistence.Reader;
import ru.rassafel.hibernate.util.SessionUtil;

import javax.persistence.NoResultException;
import java.util.List;
import ru.rassafel.hibernate.model.persistence.Article;
import java.util.Optional;

@RequiredArgsConstructor
public class ReaderDaoJpqlImpl implements ReaderDao {
    @Override
    public Optional<Reader> addArticle(Long id, Article article){
        Optional<Reader> foundById = findById(id);
        if(!foundById.isPresent()){
            System.out.println("Reader with id = " + id + " does not exist");
            return foundById;
        }
        
        if(foundById.get().getArticles().size() > 9){
            System.out.println("Reader with id = " + id + " reached maximum amount of articles");
            return foundById;
        }
        
        try(Session session = SessionUtil.createSession()){
            Transaction transaction = session.beginTransaction();
            
            Reader reader = foundById.get();
            reader.setId(id);
            List<Article> articles = reader.getArticles();
            articles.add(article);
            reader.setArticles(articles);
            session.update(reader);
            
            transaction.commit();
            return Optional.of(reader);
        }
    }
    
    @Override
    public Optional<Reader> findById(Long id) {
        try (Session session = SessionUtil.createSession()) {
            Reader reader = session
                .createQuery("from Reader r " +
                    "where r.id = ?1", Reader.class)
                .setParameter(1, id)
                .getSingleResult();
            return Optional.of(reader);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }
    
    @Override
    public List<Reader> findAll() {
        try (Session session = SessionUtil.createSession()) {
            return session
                .createQuery("from Reader", Reader.class)
                .getResultList();
        }
    }
    
    @Override
    public Reader save(Reader reader) {
        if(reader.getArticles().size() > 10){
            System.out.println("This reader has maximum amount of articles");
            return reader;
        }
        
        try (Session session = SessionUtil.createSession()) {
            Transaction transaction = session.beginTransaction();
            
            reader.getArticles().forEach(art -> session.saveOrUpdate(art));
            reader.getArticles().forEach(art -> session.saveOrUpdate(art.getAuthor()));
            session.save(reader);

            transaction.commit();
            return reader;
        }
    }
    
    @Override
    public Optional<Reader> update(Long id, Reader reader){
        Optional<Reader> foundById = findById(id);
        if(!foundById.isPresent()){
            System.out.println("Reader with id=" + id + " does not exist");
            return foundById;
        }
        
        if(reader.getArticles().size() > 10){
            System.out.println("This reader has maximum amount of articles");
            return Optional.of(reader);
        }
        
        try(Session session = SessionUtil.createSession()) {
            Transaction transaction = session.beginTransaction();
            
            reader.setId(id);
            session.update(reader);
            
            transaction.commit();
            return Optional.of(reader);
        }
    }
    
    @Override
    public Optional<Reader> deleteById(Long id){
        Optional<Reader> foundById = findById(id);
        if(!foundById.isPresent()){
            System.out.println("Reader with id=" + id + " does not exist");
            return foundById;
        }
        Reader reader = foundById.get();
        
        try(Session session = SessionUtil.createSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(reader);
            transaction.commit();
            return foundById;
        }
    }
}
