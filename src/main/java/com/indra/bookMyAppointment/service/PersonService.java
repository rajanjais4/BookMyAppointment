package com.indra.bookMyAppointment.service;

import com.indra.bookMyAppointment.common.Common;
import com.indra.bookMyAppointment.exception.ApiRequestException;
import com.indra.bookMyAppointment.model.common.Person;
import com.indra.bookMyAppointment.model.common.Role;
import com.indra.bookMyAppointment.model.professional.Professional;
import com.indra.bookMyAppointment.repository.PersonRepository;
import com.indra.bookMyAppointment.repository.ProfessionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PersonService {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    Common common;
    @Autowired
    ProfessionalRepository professionalRepository;
    public List<Person> findAll()
    {
        List<Person> personList= personRepository.findAll();
        return personList;
    }

    public Person findUserByPhoneNumber(String phoneNumber)
    {
        Person personResponse = personRepository.findByPhoneNumber(phoneNumber).orElse(null);
        if(personResponse==null)
            System.out.println("UserService.findUserByPhoneNumber - no user found - "+phoneNumber);
        else
            System.out.println("UserService.findUserByPhoneNumber user found - "+phoneNumber);
        return personResponse;
    }

    public Person saveNewUser(Person person){
        System.out.println("user to save - "+person.getName());
        if(findUserByPhoneNumber(person.getPhoneNumber())!=null)
        {
            String msg="Phone number already exists - "+person.getPhoneNumber();
            System.out.println(msg);
            throw new ApiRequestException(msg);
        }

        if(personRepository.findByEmail(person.getEmail()).orElse(null)!=null)
        {
            String msg="Email already exists - "+person.getEmail();
            System.out.println(msg);
            throw new ApiRequestException(msg);
        }
        System.out.println("Saving New User");
        String id=common.createPersonId(person);
        person.set_id(id);
        return personRepository.save(person);
    }
    public Person updatePerson(Person person){

        return personRepository.save(person);
    }
    public List<Person> searchProfessional(String name){
        return professionalRepository.searchProfessional(name);
    }
}
