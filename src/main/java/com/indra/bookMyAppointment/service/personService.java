package com.indra.bookMyAppointment.service;

import com.indra.bookMyAppointment.exception.ApiRequestException;
import com.indra.bookMyAppointment.model.common.Person;
import com.indra.bookMyAppointment.model.common.Role;
import com.indra.bookMyAppointment.model.user.User;
import com.indra.bookMyAppointment.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class personService {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    MongoTemplate mongoTemplate;
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

//        if(personRepository.findByEmail(person.getEmail()).orElse(null)!=null)
//        {
//            String msg="Email already exists - "+person.getEmail();
//            System.out.println(msg);
//            throw new ApiRequestException(msg);
//        }
        System.out.println("Saving New User");
        String id=getUserId(person);
        person.set_id(id);
        person.setRole(Role.USER);
        return personRepository.save(person);
    }
    public Person updateUser(Person person){
        System.out.println("user to update - "+person.getPhoneNumber());
        if(findUserByPhoneNumber(person.getPhoneNumber())==null)
        {
            String msg="User not found - "+person.getPhoneNumber();
            System.out.println(msg);
            throw new ApiRequestException(msg);
        }
        System.out.println("Updating User");
        String id=getUserId(person);
        person.set_id(id);
        person.setRole(Role.USER);
        return personRepository.save(person);
    }
    private String getUserId(Person user) {
        return "U_"+user.getPhoneNumber();
    }
}
