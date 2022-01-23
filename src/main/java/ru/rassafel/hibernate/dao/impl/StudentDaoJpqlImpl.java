package ru.rassafel.hibernate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.rassafel.hibernate.dao.StudentDao;
import ru.rassafel.hibernate.model.persistence.Address;
import ru.rassafel.hibernate.model.persistence.Student;
import ru.rassafel.hibernate.util.SessionUtil;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

/**
 * @author rassafel
 */
@RequiredArgsConstructor
public class StudentDaoJpqlImpl implements StudentDao {
    @Override
    public Optional<Student> findById(Long id) {
        try (Session session = SessionUtil.createSession()) {
            Student student = session
                .createQuery("from Student u " +
                    "where u.id = ?1", Student.class)
                .setParameter(1, id)
                .getSingleResult();
            return Optional.of(student);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Student> findAll() {
        try (Session session = SessionUtil.createSession()) {
            return session
                .createQuery("from Student", Student.class)
                .getResultList();
        }
    }

    @Override
    public Student save(Student student) {
        try (Session session = SessionUtil.createSession()) {
            Transaction transaction = session.beginTransaction();

            Address address = student.getAddress();
            session.save(address);
            session.save(student);

            transaction.commit();
            return student;
        }
    }

    @Override
    public Optional<Student> update(Long id, Student student) {
        Optional<Student> byId = findById(id);
        if (!byId.isPresent()) {
            System.out.println("Student with id = " + id + " does not exists");
            return byId;
        }

        try (Session session = SessionUtil.createSession()) {
            Transaction transaction = session.beginTransaction();

            student.setId(id);
            session.update(student);

            transaction.commit();
            return Optional.of(student);
        }
    }

    @Override
    public Optional<Student> deleteById(Long id) {
        Optional<Student> byId = findById(id);
        if (!byId.isPresent()) {
            System.out.println("Student with id = " + id + " does not exists");
            return byId;
        }
        Student student = byId.get();


        try (Session session = SessionUtil.createSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(student);
            transaction.commit();
            return byId;
        }
    }

    @Override
    public List<Student> findByFirstNamePrefix(String prefix) {
        try (Session session = SessionUtil.createSession()) {
            return session
                .createQuery("select s " +
                    "from Student s " +
                    "where firstName like ?1", Student.class)
                .setParameter(1, prefix + "%")
                .getResultList();
        }
    }

    @Override
    public List<Student> findByGroupId(Long groupId) {
        try (Session session = SessionUtil.createSession()) {
            return session
                .createQuery("select s " +
                    "from Student s " +
                    "where s.group.id = ?1", Student.class)
                .setParameter(1, groupId)
                .getResultList();
        }
    }

    @Override
    public List<Student> findByGroupIdWithSortedByBirthday(Long groupId) {
        try (Session session = SessionUtil.createSession()) {
            return session
                .createQuery("select s " +
                    "from Student s " +
                    "where s.group.id = :groupId " +
                    "order by s.birthday", Student.class)
                .setParameter("groupId", groupId)
                .getResultList();
        }
    }
}
