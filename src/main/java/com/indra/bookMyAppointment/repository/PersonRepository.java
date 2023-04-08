package com.indra.bookMyAppointment.repository;

import com.indra.bookMyAppointment.model.common.Person;
import com.indra.bookMyAppointment.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PersonRepository extends MongoRepository<Person,String> {
    Optional<Person> findByPhoneNumber(String phoneNumber);

    Optional<Person> findByEmail(String email);
}
