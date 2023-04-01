package com.indra.bookMyAppointment.repository;

import com.indra.bookMyAppointment.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findByPhoneNumber(String phoneNumber);

    User findByEmail(String email);
}
