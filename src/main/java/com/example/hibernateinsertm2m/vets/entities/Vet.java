package com.example.hibernateinsertm2m.vets.entities;

import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "vet")
public class Vet {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(name = "vet_speciality",
            joinColumns = @JoinColumn(name = "vet_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "speciality_id", referencedColumnName = "id"))
    private List<Speciality> specialities = new ArrayList<>();

    public List<Speciality> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(List<Speciality> specialities) {
        this.specialities = specialities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addSpeciality(Speciality speciality){
        specialities.add(speciality);
        speciality.getVets().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Vet vet = (Vet) o;
        return id != null && Objects.equals(id, vet.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}