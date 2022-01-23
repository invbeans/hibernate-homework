package ru.rassafel.hibernate.dao.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.rassafel.hibernate.dao.GroupDao;
import ru.rassafel.hibernate.model.persistence.Group;
import ru.rassafel.hibernate.util.SessionUtil;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

/**
 * @author rassafel
 */
public class GroupDaoJpqlImpl implements GroupDao {
    @Override
    public Optional<Group> findById(Long id) {
        try (Session session = SessionUtil.createSession()) {
            Group group = session
                .createQuery("from Group g " +
                    "where g.id = " + id, Group.class)
                .getSingleResult();
            return Optional.of(group);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Group> findAll() {
        try (Session session = SessionUtil.createSession()) {
            return session
                .createQuery("from Group", Group.class)
                .getResultList();
        }
    }

    @Override
    public Group save(Group group) {
        try (Session session = SessionUtil.createSession()) {
            Transaction transaction = session.beginTransaction();

            session.save(group);

            transaction.commit();
            return group;
        }
    }

    @Override
    public Optional<Group> update(Long key, Group group) {
        return Optional.empty();
    }

    @Override
    public Optional<Group> deleteById(Long id) {
        Optional<Group> byId = findById(id);
        if (!byId.isPresent()) {
            System.out.println("Group with id = " + id + " does not exists");
            return byId;
        }
        Group group = byId.get();

        try (Session session = SessionUtil.createSession()) {
            Transaction transaction = session.beginTransaction();

            session.delete(group);

            transaction.commit();
            return byId;
        }
    }

    @Override
    public List<Group> findWhereCountStudentsEqualsThenN(int countStudents) {
        try (Session session = SessionUtil.createSession()) {
            return session.createQuery("from Group g " +
                    "where size(g.students) = ?1 ", Group.class)
                .setParameter(1, countStudents)
                .getResultList();
        }
    }

    @Override
    public Optional<Group> findByGroupIdWithStudents(Long groupId) {
        try (Session session = SessionUtil.createSession()) {
            Group result = session.createQuery("from Group g " +
                    "join fetch g.students " +
                    "where g.id = ?1 ", Group.class)
                .setParameter(1, groupId)
                .getSingleResult();
            return Optional.of(result);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public OptionalInt countStudentsInGroup(Long groupId) {
        try (Session session = SessionUtil.createSession()) {
            Integer result = session.createQuery("select size(g.students) " +
                    "from Group g " +
                    "where g.id = ?1", Integer.class)
                .setParameter(1, groupId)
                .getSingleResult();
            return OptionalInt.of(result);
        } catch (NoResultException ex) {
            return OptionalInt.empty();
        }
    }
}
