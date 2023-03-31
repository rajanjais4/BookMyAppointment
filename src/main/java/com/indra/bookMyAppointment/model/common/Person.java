package com.indra.bookMyAppointment.model.common;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.List;
@Data
public class Person {
    @Id
    private String _id;
    private String name;
    @Indexed(unique = true)
    private String email;
    @Indexed(unique = true)
    private String phoneNumber;
    private List<Address> address;
}
