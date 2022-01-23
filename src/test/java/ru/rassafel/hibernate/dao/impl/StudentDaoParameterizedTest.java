package ru.rassafel.hibernate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.rassafel.hibernate.dao.StudentDao;
import ru.rassafel.hibernate.model.persistence.Student;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.util.Objects.isNull;
import static org.junit.Assert.*;

/**
 * @author rassafel
 */
@RunWith(Parameterized.class)
@RequiredArgsConstructor
public class StudentDaoParameterizedTest {
    private final StudentDao dao;

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Collection<Object[]> instantsToTest() {
        return Arrays.asList(
            new Object[]{new StudentDaoJpqlImpl()},
            new Object[]{new StudentDaoNativeImpl()},
            new Object[]{new StudentDaoNamedImpl()},
            new Object[]{new StudentDaoCriteriaImpl()}
        );
    }

    @Test
    public void findByGroupIdWithSortedByBirthday() {
        Long groupId = 1L;

        List<Student> actual = dao.findByGroupIdWithSortedByBirthday(groupId);

        assertFalse(actual.isEmpty());

        Student prev = null;

        for (Student student : actual) {
            assertEquals(groupId, student.getGroup().getId());

            assertTrue(isNull(prev) || prev.getBirthday().isBefore(student.getBirthday()));
            prev = student;
        }
    }

    @Test
    public void findByFirstNamePrefix() {
        String startsName = "Al";

        List<Student> students = dao.findByFirstNamePrefix(startsName);

        assertFalse(students.isEmpty());

        for (Student student : students) {
            assertTrue(student.getFirstName().startsWith(startsName));
        }
    }

    @Test
    public void findByGroupId() {
        Long groupId = 1L;

        List<Student> actual = dao.findByGroupId(groupId);

        assertFalse(actual.isEmpty());

        for (Student student : actual) {
            assertEquals(groupId, student.getGroup().getId());
        }
    }
}
