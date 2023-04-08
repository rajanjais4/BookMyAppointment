package com.indra.bookMyAppointment.auth;

import com.indra.bookMyAppointment.model.common.Person;
import com.indra.bookMyAppointment.model.common.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String name;
  private String email;
  private String phoneNumber;
  private String password;
  private Role role;
}
