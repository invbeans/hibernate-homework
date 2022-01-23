package ru.rassafel.hibernate.dao;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ru.rassafel.hibernate.dao.impl.*;
import ru.rassafel.hibernate.model.persistence.Address;
import ru.rassafel.hibernate.model.persistence.Group;
import ru.rassafel.hibernate.model.persistence.Student;

import java.time.LocalDate;

/**
 * @author rassafel
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        StudentDaoParameterizedTest.class,
        ReadGroupDaoImplTest.class,
        ReadStudentDaoImplTest.class,
        WriteStudentDaoImpl.class
})
public class HibernateTest {
    public static final Address ADDRESS_1 = new Address();
    public static final Address ADDRESS_2 = new Address();
    public static final Student STUDENT_1 = new Student();
    public static final Student STUDENT_2 = new Student();
    public static final Group GROUP = new Group();
    private final static StudentDao STUDENT_DAO = new StudentDaoJpqlImpl();
    private final static GroupDao GROUP_DAO = new GroupDaoJpqlImpl();

    @BeforeClass
    public static void onStart() {
        deleteData();
        loadData();
    }

    @AfterClass
    public static void onEnd() {
//        deleteData();
    }

    public static void deleteData() {
        STUDENT_DAO
                .findAll()
                .forEach(stud -> STUDENT_DAO.deleteById(stud.getId()));
        GROUP_DAO
                .findAll()
                .forEach(group -> GROUP_DAO.deleteById(group.getId()));
    }

    public static void loadData() {

        GROUP.setName("Test group name");
        GROUP_DAO.save(GROUP);

        ADDRESS_1.setAddress("Test address");
        ADDRESS_2.setAddress("Other test address");

        STUDENT_1.setAddress(ADDRESS_1);
        STUDENT_1.setFirstName("Ivan");
        STUDENT_1.setLastName("Ivanov");
        STUDENT_1.setBirthday(LocalDate.of(1999, 1, 1));
        STUDENT_1.setGroup(GROUP);
        STUDENT_1.setPhone("Test 1");

        STUDENT_2.setAddress(ADDRESS_2);
        STUDENT_2.setFirstName("Alex");
        STUDENT_2.setLastName("Wilders");
        STUDENT_2.setBirthday(LocalDate.of(1980, 3, 4));
        STUDENT_2.setGroup(GROUP);
        STUDENT_2.setPhone("Test 2");

        STUDENT_DAO.save(STUDENT_1);
        STUDENT_DAO.save(STUDENT_2);
    }
}
