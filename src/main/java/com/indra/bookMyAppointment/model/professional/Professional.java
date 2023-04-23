package com.indra.bookMyAppointment.model.professional;

import com.indra.bookMyAppointment.model.common.Person;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "person")
public class Professional extends Person {
    @Indexed()
    private String profession;
    private String qualification;
    @Indexed()
    private String summary;
    private String description;
    private String experience;
}
