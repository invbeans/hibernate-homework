package ru.rassafel.hibernate.dao.impl;

import org.hibernate.Session;
import ru.rassafel.hibernate.dao.StudentDao;
import ru.rassafel.hibernate.model.persistence.Student;
import ru.rassafel.hibernate.util.SessionUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * @author rassafel
 */
public class StudentDaoCriteriaImpl implements StudentDao {
    @Override
    public Optional<Student> findById(Long key) {
        return Optional.empty();
    }

    @Override
    public List<Student> findAll() {
        return null;
    }

    @Override
    public Student save(Student student) {
        return null;
    }

    @Override
    public Optional<Student> update(Long key, Student student) {
        return Optional.empty();
    }

    @Override
    public Optional<Student> deleteById(Long key) {
        return Optional.empty();
    }

    @Override
    public List<Student> findByFirstNamePrefix(String prefix) {
        try(Session session = SessionUtil.createSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Student> builderQuery = builder.createQuery(Student.class);
            Root<Student> root = builderQuery.from(Student.class);

            builderQuery.select(root)
                .where(builder.like(root.get("firstName"), prefix+"%"));

            return session.createQuery(builderQuery).getResultList();
        }
    }

    @Override
    public List<Student> findByGroupId(Long groupId) {
        try(Session session = SessionUtil.createSession()) {

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Student> builderQuery = builder.createQuery(Student.class);
            Root<Student> root = builderQuery.from(Student.class);

            builderQuery.select(root)
                .where(builder.equal(root.get("group").get("id"), groupId));

            return session.createQuery(builderQuery).getResultList();
        }
    }

    @Override
    public List<Student> findByGroupIdWithSortedByBirthday(Long groupId) {
        try(Session session = SessionUtil.createSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Student> builderQuery = builder.createQuery(Student.class);
            Root<Student> root = builderQuery.from(Student.class);

            builderQuery.select(root)
                .where(builder.equal(root.get("group").get("id"), groupId))
                .orderBy(builder.asc(root.get("birthday")));

            return session.createQuery(builderQuery).getResultList();
        }
    }
}
