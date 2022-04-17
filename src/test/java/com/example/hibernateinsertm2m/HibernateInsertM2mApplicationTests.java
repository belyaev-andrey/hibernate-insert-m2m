package com.example.hibernateinsertm2m;

import com.example.hibernateinsertm2m.vets.entities.Speciality;
import com.example.hibernateinsertm2m.vets.entities.Vet;
import com.example.hibernateinsertm2m.vets.repositories.SpecialityRepository;
import com.example.hibernateinsertm2m.vets.repositories.VetRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@TestPropertySource(locations = {"classpath:test-application.properties"})
@SpringBootTest
class HibernateInsertM2mApplicationTests {

	@Autowired
	VetRepository vetRepository;

	@Autowired
	SpecialityRepository specialityRepository;

	@Test
	void contextLoads() {
	}

	@Test
	@Transactional
	@Rollback
	void testAddValueToListOnOwningSide_DirectAdd() {
		Vet vet = vetRepository.findById(1L).orElseThrow();
		Speciality speciality = specialityRepository.findById(2L).orElseThrow();
		vet.getSpecialities().add(speciality);
		vetRepository.saveAndFlush(vet);
	}

	@Test
	@Transactional
	@Rollback
	void testAddValueToListOnOwningSide_SyncMethod() {
		Vet vet = vetRepository.findById(1L).orElseThrow();
		Speciality speciality = specialityRepository.findById(2L).orElseThrow();
		vet.addSpeciality(speciality);
		vetRepository.saveAndFlush(vet);
	}

}
