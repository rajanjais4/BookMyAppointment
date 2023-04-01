package com.indra.bookMyAppointment.token;

import com.indra.bookMyAppointment.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends MongoRepository<Token,String> {

  List<Token> findAllValidTokenByUser(String id);

  Optional<Token> findByToken(String token);
}
