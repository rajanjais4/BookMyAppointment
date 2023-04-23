package com.indra.bookMyAppointment.repository.impl;

import com.indra.bookMyAppointment.model.common.Person;
import com.indra.bookMyAppointment.model.common.Role;
import com.indra.bookMyAppointment.repository.ProfessionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProfessionalRepositoryImpl implements ProfessionalRepository {
    @Autowired
    MongoTemplate mongoTemplate;
    @Override
    public List<Person> searchProfessional(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("role").is(Role.PROFESSIONAL.name()));
        if(name!=null & !name.isEmpty())
            query.addCriteria(Criteria.where("name").regex(name));
        return mongoTemplate.find(query,Person.class);
    }
}
