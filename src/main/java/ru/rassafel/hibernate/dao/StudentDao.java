package ru.rassafel.hibernate.dao;

import ru.rassafel.hibernate.model.persistence.Student;

import java.util.List;

/**
 * @author rassafel
 */
public interface StudentDao extends GenericDao<Student, Long> {
    List<Student> findByFirstNamePrefix(String prefix);

    List<Student> findByGroupId(Long groupId);

    List<Student> findByGroupIdWithSortedByBirthday(Long groupId);
}
