package com.example.hibernateinsertm2m.vets.repositories;

import com.example.hibernateinsertm2m.vets.entities.Vet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VetRepository extends JpaRepository<Vet, Long> {
}