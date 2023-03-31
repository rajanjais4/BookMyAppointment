package com.indra.bookMyAppointment.model.user;

import com.indra.bookMyAppointment.model.common.Address;
import com.indra.bookMyAppointment.model.common.Person;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "users")
@Data
public class User extends Person {

}
