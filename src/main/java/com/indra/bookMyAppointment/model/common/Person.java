package com.indra.bookMyAppointment.model.common;

import lombok.*;
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
    @NonNull
    private String name;
    @Indexed(unique = true)
    @NonNull
    private String email;
    @Indexed(unique = true)
    @NonNull
    private String phoneNumber;
    @NonNull
    private String password;
    private List<Address> address;
    private Role role;

    public enum Role {

        USER,
        ADMIN,
        PROFESSIONAL

    }
}
