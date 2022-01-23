package ru.rassafel.hibernate.dao;

import ru.rassafel.hibernate.model.persistence.Group;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

/**
 * @author rassafel
 */
public interface GroupDao extends GenericDao<Group, Long> {
    List<Group> findWhereCountStudentsEqualsThenN(int n);

    Optional<Group> findByGroupIdWithStudents(Long groupId);

    OptionalInt countStudentsInGroup(Long groupId);
}
