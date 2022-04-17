package com.example.hibernateinsertm2m.vets.repositories;

import com.example.hibernateinsertm2m.vets.entities.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialityRepository extends JpaRepository<Speciality, Long> {
}