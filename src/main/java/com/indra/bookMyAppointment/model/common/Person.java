package com.indra.bookMyAppointment.model.common;

import com.indra.bookMyAppointment.model.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    private String _id;
    private String name;
    @Indexed(unique = true)
    private String email;
    @Indexed(unique = true)
    private String phoneNumber;
    private String password;
    private List<Address> address;
    private Role role;
}
