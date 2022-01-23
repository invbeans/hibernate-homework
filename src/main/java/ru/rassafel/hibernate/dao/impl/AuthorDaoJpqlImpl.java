package ru.rassafel.hibernate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.rassafel.hibernate.dao.AuthorDao;
import ru.rassafel.hibernate.util.SessionUtil;

import javax.persistence.NoResultException;
import java.util.List;
import ru.rassafel.hibernate.model.persistence.Author;
import java.util.Optional;

@RequiredArgsConstructor
public class AuthorDaoJpqlImpl implements AuthorDao {
    @Override
    public List<Author> findByMinArticlesAmount(int amount){
        try(Session session = SessionUtil.createSession()) {
            return session.createQuery("from Author a " +
                    "where size(a.articles) >= ?1 ", Author.class)
                    .setParameter(1, amount)
                    .getResultList();
        }
    }
    
    @Override
    public Optional<Author> findById(Long id) {
        try (Session session = SessionUtil.createSession()) {
            Author author = session
                .createQuery("from Author a " +
                    "where a.id = " + id, Author.class)
                .getSingleResult();
            return Optional.of(author);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }
    
    @Override
    public List<Author> findAll() {
        try (Session session = SessionUtil.createSession()) {
            return session
                .createQuery("from Author", Author.class)
                .getResultList();
        }
    }
    
    @Override
    public Author save(Author author) {
        try (Session session = SessionUtil.createSession()) {
            Transaction transaction = session.beginTransaction();
            
            author.getArticles().forEach(art -> session.saveOrUpdate(art));
            session.save(author);

            transaction.commit();
            return author;
        }
    }
    
    @Override
    public Optional<Author> update(Long id, Author author){
        Optional<Author> foundById = findById(id);
        if(!foundById.isPresent()){
            System.out.println("Author with id=" + id + " does not exist");
            return foundById;
        }
        
        try(Session session = SessionUtil.createSession()) {
            Transaction transaction = session.beginTransaction();
            
            author.setId(id);
            session.update(author);
            
            transaction.commit();
            return Optional.of(author);
        }
    }
    
    @Override
    public Optional<Author> deleteById(Long id){
        Optional<Author> foundById = findById(id);
        if(!foundById.isPresent()){
            System.out.println("Author with id=" + id + " does not exist");
            return foundById;
        }
        Author author = foundById.get();
        
        try(Session session = SessionUtil.createSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(author);
            transaction.commit();
            return foundById;
        }
    }
    
}