package ru.rassafel.hibernate.dao.impl;

import org.hibernate.Session;
import ru.rassafel.hibernate.dao.StudentDao;
import ru.rassafel.hibernate.model.persistence.Student;
import ru.rassafel.hibernate.util.SessionUtil;

import java.util.List;
import java.util.Optional;

/**
 * @author rassafel
 */
public class StudentDaoNativeImpl implements StudentDao {
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
            return session.createNativeQuery("select * " +
                    "from student as s " +
                    "where s.first_name like ?1", Student.class)
                .setParameter(1, prefix + "%")
                .getResultList();
        }
    }

    @Override
    public List<Student> findByGroupId(Long groupId) {
        try(Session session = SessionUtil.createSession()) {
            return session.createNativeQuery("select * " +
                    "from student as s " +
                    "where s.group_id = ?1", Student.class)
                .setParameter(1, groupId)
                .getResultList();
        }
    }

    @Override
    public List<Student> findByGroupIdWithSortedByBirthday(Long groupId) {
        try(Session session = SessionUtil.createSession()) {
            return session.createNativeQuery("select * " +
                    "from student as s " +
                    "where s.group_id = ?1 " +
                    "order by s.birthday", Student.class)
                .setParameter(1, groupId)
                .getResultList();
        }
    }
}
