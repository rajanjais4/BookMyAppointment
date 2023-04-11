package com.indra.bookMyAppointment.auth;

import com.indra.bookMyAppointment.config.JwtService;
import com.indra.bookMyAppointment.exception.ApiRequestException;
import com.indra.bookMyAppointment.model.common.Person;
import com.indra.bookMyAppointment.model.common.Role;
import com.indra.bookMyAppointment.model.professional.Professional;
import com.indra.bookMyAppointment.model.user.User;
import com.indra.bookMyAppointment.repository.PersonRepository;
import com.indra.bookMyAppointment.service.PersonService;
import com.indra.bookMyAppointment.token.Token;
import com.indra.bookMyAppointment.token.TokenRepository;
import com.indra.bookMyAppointment.token.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final PersonService service;
  private final PersonRepository repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(RegisterRequest request) {
    Person person=new Person();
    if (request.getRole()==Role.USER){
      System.out.println("Registering new user");
      person=new User();
    } else if (request.getRole()==Role.PROFESSIONAL) {
      System.out.println("Registering new PROFESSIONAL");
      person=new Professional();
    }
    else {
      throw new ApiRequestException("Role should be either USER or PROFESSIONAL");
    }
    person.setName(request.getName());
    person.setEmail(request.getEmail());
    person.setPhoneNumber(request.getPhoneNumber());
    person.setPassword(passwordEncoder.encode(request.getPassword()));
    person.setRole(request.getRole());
    var savedUser = service.saveNewUser(person);
    var jwtToken = jwtService.generateToken(person.getRole(),person);
    saveUserToken(savedUser, jwtToken);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getPhoneNumber(),
            request.getPassword()
        )
    );
    System.out.println("AuthenticationService.authenticate.request.getPhoneNumber() - "+request.getPhoneNumber());
    var person = repository.findByPhoneNumber(request.getPhoneNumber())
        .orElseThrow();
    var jwtToken = jwtService.generateToken(person.getRole(),person);
    System.out.println("AuthenticationService.authenticate.jwtToken - "+jwtToken);
    revokeAllUserTokens(person);
    saveUserToken(person, jwtToken);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }

  private void saveUserToken(Person user, String jwtToken) {
    var token = Token.builder()
        .user(user)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(Person person) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(person.get_id());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }
}
