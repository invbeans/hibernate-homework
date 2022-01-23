package ru.rassafel.hibernate.dao.impl;

import org.junit.Test;
import ru.rassafel.hibernate.dao.StudentDao;
import ru.rassafel.hibernate.model.persistence.Address;
import ru.rassafel.hibernate.model.persistence.Student;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.*;
import static ru.rassafel.hibernate.dao.HibernateTest.*;

/**
 * @author rassafel
 */
public class WriteStudentDaoImpl {
    private final StudentDao dao = new StudentDaoJpqlImpl();

    @Test
    public void save() {
        Address address = new Address();
        address.setAddress("Test address for save");

        Student actual = new Student();
        actual.setAddress(address);
        actual.setFirstName("Test first name");
        actual.setLastName("Test last name");
        actual.setBirthday(LocalDate.of(2000, 1, 1));
        actual.setPhone("Phone 1");
        actual.setGroup(GROUP);
        actual = dao.save(actual);

        assertTrue(actual.getId() != null);
    }

    @Test
    public void update() {
        String expected = "Test last name";
        Student actual = new Student();
        actual.setId(STUDENT_1.getId());
        actual.setFirstName(STUDENT_1.getFirstName());
        actual.setBirthday(STUDENT_1.getBirthday());
        actual.setLastName(expected);
        actual.setAddress(ADDRESS_1);
        actual.setGroup(GROUP);
        actual.setPhone(STUDENT_1.getPhone());
        dao.update(1L, actual);

        Optional<Student> optionalStudent = dao.findById(1L);
        assertTrue(optionalStudent.isPresent());

        Student student = optionalStudent.get();
        assertEquals(expected, student.getLastName());
    }

    @Test
    public void delete() {
        Long id = 2L;

        dao.deleteById(id);

        Optional<Student> optionalStudent = dao.findById(id);
        assertFalse(optionalStudent.isPresent());
    }
}
