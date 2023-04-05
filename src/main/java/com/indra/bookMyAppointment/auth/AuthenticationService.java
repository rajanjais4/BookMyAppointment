package com.indra.bookMyAppointment.auth;

import com.indra.bookMyAppointment.config.JwtService;
import com.indra.bookMyAppointment.model.common.Person;
import com.indra.bookMyAppointment.model.user.User;
import com.indra.bookMyAppointment.repository.UserRepository;
import com.indra.bookMyAppointment.service.UserService;
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
  private final UserService service;
  private final UserRepository repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(RegisterRequest request) {
    var user = new User();
    user.setName(request.getName());
    user.setEmail(request.getEmail());
    user.setPhoneNumber(request.getPhoneNumber());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setRole(Person.Role.USER);
    var savedUser = service.saveNewUser(user);
    var jwtToken = jwtService.generateToken(user);
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
    var user = repository.findByPhoneNumber(request.getPhoneNumber())
        .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    System.out.println("AuthenticationService.authenticate.jwtToken - "+jwtToken);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }

  private void saveUserToken(User user, String jwtToken) {
    var token = Token.builder()
        .user(user)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.get_id());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }
}
