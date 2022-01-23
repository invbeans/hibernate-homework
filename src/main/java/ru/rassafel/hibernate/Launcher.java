package ru.rassafel.hibernate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.rassafel.hibernate.dao.ArticleDao;
import ru.rassafel.hibernate.dao.AuthorDao;
import ru.rassafel.hibernate.dao.ReaderDao;
import ru.rassafel.hibernate.dao.impl.ArticleDaoJpqlImpl;
import ru.rassafel.hibernate.dao.impl.AuthorDaoJpqlImpl;
import ru.rassafel.hibernate.dao.impl.ReaderDaoJpqlImpl;
import ru.rassafel.hibernate.model.persistence.Article;
import ru.rassafel.hibernate.model.persistence.Author;
import ru.rassafel.hibernate.model.persistence.Reader;
import ru.rassafel.hibernate.util.SessionUtil;


/**
 * @author rassafel
 */
public class Launcher {
    private static AuthorDao authorDao;
    private static ArticleDao articleDao;
    private static ReaderDao readerDao;
    
    public static void main(String[] args) {
        Launcher app = new Launcher();
        authorDao = new AuthorDaoJpqlImpl();
        readerDao = new ReaderDaoJpqlImpl();
        articleDao = new ArticleDaoJpqlImpl();
        
        Reader reader1 = new Reader();
        Reader reader2 = new Reader();
        Article article1 = new Article();
        Article article2 = new Article();
        Author author1 = new Author();
        
        List<Article> artList = new ArrayList<>();
        List<Reader> reaList = new ArrayList<>();
        
        reader1.setFirstName("Ivan"); reader1.setLastName("Ivanov"); reader1.setEmail("email.com");
        reader2.setFirstName("Petr"); reader2.setLastName("Petrov"); reader2.setEmail("pochta.ru");
        reaList.add(reader1); reaList.add(reader2);
        
        article1.setName("First article"); article1.setWriteDate(LocalDate.of(2017, 3, 20)); article1.setReaders(reaList);
        article2.setName("Second article"); article2.setWriteDate(LocalDate.of(2019, 5, 12)); article2.setReaders(reaList);
        artList.add(article1); artList.add(article2);
        
        author1.setFirstName("Author_name"); author1.setLastName("Author_last_name"); author1.setBirthday(LocalDate.of(1995, 8, 24)); author1.setArticles(artList);
        reader1.setArticles(artList); reader2.setArticles(artList);
        article1.setAuthor(author1); article2.setAuthor(author1);
        
        //createAuthor(author1);
        //createArticle(article2);
        createReader(reader1);
    }
    
    private static Author createAuthor(Author author){
        //author.getArticles().forEach(art -> articleDao.save(art));
        return authorDao.save(author);
    }
    
    private static Reader createReader(Reader reader){
        return readerDao.save(reader);
    }
    
    private static Article createArticle(Article article){
        return articleDao.save(article);
    }
        
    /*public Author saveAuthor(Author author){
        Session session = SessionUtil.createSession();
        Transaction transaction = session.beginTransaction();
        
        author.getArticles().forEach(art -> session.save(art));
        //session.save(author.getArticles());
        session.save(author);
        
        transaction.commit();
        session.close();
        
        return author;
    }
    
    //пора прекратить строить эту какафонию от статей
    public Article saveArticle(Article article){
        Session session = SessionUtil.createSession();
        Transaction transaction = session.beginTransaction();

        session.save(article);

        transaction.commit();  
        session.close();

        return article;
    }
    
    public Article changeAuthor(Article article, Author author){
        Session session = SessionUtil.createSession();
        Transaction transaction = session.beginTransaction();

        session.save(author);
        article.setAuthor(author);
        session.update(article);
        //session.save(article.getAuthor());

        transaction.commit();  
        session.close();

        return article;
    }
    
    public Article deleteArticle(Article article){
        Session session = SessionUtil.createSession();
        Transaction transaction = session.beginTransaction();

        session.delete(article);

        transaction.commit();  
        session.close();

        return article;
    }
    
    public List<Article> getArticles(){
        Session session = SessionUtil.createSession();
        
        //на jpql, объектно-ориентированные запросы все объекты сущности
        List<Article> articles = session
                .createQuery("from Article", Article.class)
                .getResultList();
        
        session.close();     
        return articles;
    }
    
    public Optional<Article> getArticleById(Long id){
        Session session = SessionUtil.createSession();
        
        //на jpql, объектно-ориентированные запросы все объекты сущности
        Optional<Article> article = session
                .createQuery("from Article u where u.id = " + id, Article.class)
                .uniqueResultOptional();
        
        session.close();     
        return article;
    }*/
}
