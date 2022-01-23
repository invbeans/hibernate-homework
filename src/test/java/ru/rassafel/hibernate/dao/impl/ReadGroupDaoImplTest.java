package ru.rassafel.hibernate.dao.impl;

import org.hibernate.LazyInitializationException;
import org.junit.Test;
import ru.rassafel.hibernate.dao.GroupDao;
import ru.rassafel.hibernate.model.persistence.Group;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import static org.junit.Assert.*;

/**
 * @author rassafel
 */
public class ReadGroupDaoImplTest {
    private final GroupDao dao = new GroupDaoJpqlImpl();

    @Test
    public void countStudentsInGroup() {
        int expected = 2;

        OptionalInt optionalInt = dao.countStudentsInGroup(1L);

        assertTrue(optionalInt.isPresent());

        int actual = optionalInt.getAsInt();

        assertEquals(expected, actual);
    }

    @Test
    public void findWhereCountStudentsEqualsThenN() {
        int expectedCount = 2;
        List<Group> actual = dao.findWhereCountStudentsEqualsThenN(expectedCount);

        assertFalse(actual.isEmpty());

        for (Group group : actual) {
            OptionalInt optionalInt = dao.countStudentsInGroup(group.getId());
            assertTrue(optionalInt.isPresent());
            int actualCount = optionalInt.getAsInt();
            assertEquals(expectedCount, actualCount);
        }
    }

    @Test
    public void findByGroupIdWithStudents() {
        Optional<Group> actual = dao.findByGroupIdWithStudents(1L);

        assertTrue(actual.isPresent());

        Group group = actual.get();

        assertFalse(group.getStudents().isEmpty());
    }

    @Test(expected = LazyInitializationException.class)
    public void catchLazyInitializationExceptionFromFindId() {
        Optional<Group> actual = dao.findById(1L);

        assertTrue(actual.isPresent());

        Group group = actual.get();

        assertNull(group.getStudents());
    }
}
