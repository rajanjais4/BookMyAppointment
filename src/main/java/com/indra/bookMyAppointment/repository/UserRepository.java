package com.indra.bookMyAppointment.repository;

import com.indra.bookMyAppointment.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
    User findByPhoneNumber(String phoneNumber);

    User findByEmail(String email);
}
