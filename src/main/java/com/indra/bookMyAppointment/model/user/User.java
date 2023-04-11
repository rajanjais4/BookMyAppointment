package com.indra.bookMyAppointment.model.user;

import com.indra.bookMyAppointment.model.common.Person;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Document(collection = "person")
@NoArgsConstructor
public class User extends Person {


}
