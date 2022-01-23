package ru.rassafel.hibernate.dao.impl;

import org.junit.Test;
import ru.rassafel.hibernate.dao.StudentDao;
import ru.rassafel.hibernate.model.persistence.Student;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static ru.rassafel.hibernate.dao.HibernateTest.STUDENT_1;
import static ru.rassafel.hibernate.dao.HibernateTest.STUDENT_2;

/**
 * @author rassafel
 */
public class ReadStudentDaoImplTest {
    private final StudentDao dao = new StudentDaoJpqlImpl();

    @Test
    public void findUserById() {
        Student expected = STUDENT_1;
        Optional<Student> actual = dao.findById(1L);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    public void findAll() {
        List<Student> expected = Arrays.asList(STUDENT_1, STUDENT_2);

        List<Student> actual = dao.findAll();

        assertTrue(!actual.isEmpty());
        assertTrue(actual.size() == expected.size());

        for (int i = 0; i < actual.size(); i++) {
            assertEquals(expected.get(i), actual.get(i));
        }
    }

    @Test
    public void findByStartsFirstName() {
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
            System.out.println("student = " + student);
            assertEquals(groupId, student.getGroup().getId());
        }
    }
}