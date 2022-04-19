package com.example.hibernateinsertm2m;

import com.example.hibernateinsertm2m.vets.entities.Speciality;
import com.example.hibernateinsertm2m.vets.entities.Vet;
import com.example.hibernateinsertm2m.vets.repositories.SpecialityRepository;
import com.example.hibernateinsertm2m.vets.repositories.VetRepository;
import net.ttddyy.dsproxy.asserts.PreparedExecution;
import net.ttddyy.dsproxy.asserts.ProxyTestDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertFalse;

@Import(TestProxyDatasourceConfiguration.class)
@TestPropertySource(locations = {"classpath:test-application.properties"})
@SpringBootTest
class HibernateInsertM2mApplicationTests {

    @Autowired
    VetRepository vetRepository;

    @Autowired
    SpecialityRepository specialityRepository;

    @Autowired
    private ProxyTestDataSource ds;

    @Test
    void contextLoads() {
    }

    @BeforeEach
    void setUp() {
        //Clean up previous SQL statements
        ds.reset();
    }

    @Test
    @Transactional
    @Rollback
    void testAddValueToListOnOwningSide_AddToOwning() {
        Vet vet = vetRepository.findById(1L).orElseThrow();
        Speciality speciality = specialityRepository.findById(2L).orElseThrow();
        vet.getSpecialities().add(speciality);
        vetRepository.saveAndFlush(vet);
        ds.getQueryExecutions().forEach(qe -> {
            if (qe instanceof PreparedExecution preparedExecution) {
                String query = preparedExecution.getQuery();
                assertFalse(query.toLowerCase().contains("delete"),
                        "Statements should not contain 'delete' SQL, offending statement: "+ query);
            }
        });
    }

    @Test
    @Transactional
    @Rollback
    void testAddValueToListOnOwningSide_AddToOwning_SyncMethod() {
        Vet vet = vetRepository.findById(1L).orElseThrow();
        Speciality speciality = specialityRepository.findById(2L).orElseThrow();
        vet.addSpeciality(speciality);
        vetRepository.saveAndFlush(vet);
        ds.getQueryExecutions().forEach(qe -> {
            if (qe instanceof PreparedExecution preparedExecution) {
                String query = preparedExecution.getQuery();
                assertFalse(query.toLowerCase().contains("delete"),
                        "Statements should not contain 'delete' SQL, offending statement: "+ query);
            }
        });
    }

    @Test
    @Transactional
    @Rollback
    void testAddValueToListOnOwningSide_AddToNonOwning_SyncMethod() {
        Vet vet = vetRepository.findById(1L).orElseThrow();
        Speciality speciality = specialityRepository.findById(2L).orElseThrow();
        speciality.addVet(vet);
        specialityRepository.saveAndFlush(speciality);
        ds.getQueryExecutions().forEach(qe -> {
            if (qe instanceof PreparedExecution preparedExecution) {
                String query = preparedExecution.getQuery();
                assertFalse(query.toLowerCase().contains("delete"),
                        "Statements should not contain 'delete' SQL, offending statement: "+ query);
            }
        });
    }

}
