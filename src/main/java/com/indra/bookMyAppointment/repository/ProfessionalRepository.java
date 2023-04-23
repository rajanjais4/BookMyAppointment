package com.indra.bookMyAppointment.repository;

import com.indra.bookMyAppointment.model.common.Person;

import java.util.List;

public interface ProfessionalRepository {
    List<Person> searchProfessional(String name);
}
