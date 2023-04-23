package com.indra.bookMyAppointment.controller;

import com.indra.bookMyAppointment.common.Common;
import com.indra.bookMyAppointment.exception.ApiRequestException;
import com.indra.bookMyAppointment.model.common.Person;
import com.indra.bookMyAppointment.model.common.Role;
import com.indra.bookMyAppointment.model.professional.Professional;
import com.indra.bookMyAppointment.model.user.User;
import com.indra.bookMyAppointment.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonService personService;

    @Autowired
    Common common;

//    TODO: implement admin
    @RolesAllowed("ADMINS")
    @GetMapping("/getAllPersons")
    public ResponseEntity<List<Person>> getAllPerson()
    {
        return ResponseEntity.ok(personService.findAll());
    }

    @GetMapping("/getPersonByPhoneNumber")
    public ResponseEntity<Person> getPersonByPhoneNumber(@RequestParam("phoneNumber")String phoneNumber,
                                                      @RequestHeader (name="Authorization") String token)
    {
        String userAuthPhoneNumber= common.getPersonPhoneNumberByToken(token);
        if(!phoneNumber.equals(userAuthPhoneNumber))
            throw new ApiRequestException("user- "+userAuthPhoneNumber+" querying for another user");
        Person user= personService.findUserByPhoneNumber(phoneNumber);
        if(user==null)
            throw new ApiRequestException("User not found");
        return ResponseEntity.ok(user);
    }
    @GetMapping("/getPersonByAuthToken")
    public ResponseEntity<Person> getPersonByAuth(@RequestHeader (name="Authorization") String token)
    {
        String userAuthPhoneNumber= common.getPersonPhoneNumberByToken(token);
        Person user= personService.findUserByPhoneNumber(userAuthPhoneNumber);
        if(user==null)
            throw new ApiRequestException("User not found");
        return ResponseEntity.ok(user);
    }

    @PostMapping("/updateProfessional")
    public ResponseEntity<Person> updateProfessional(@RequestBody Professional professional,
                                                   @RequestHeader(name="Authorization") String token){
        common.personUpdateRequestCheck(professional,token,Role.PROFESSIONAL);
        Person person=common.createUpdatedProfessional(professional);
        Person userResponse = personService.updatePerson(person);
        if(userResponse==null)
            throw new ApiRequestException("professional not updated");
        return ResponseEntity.ok(userResponse);
    }
    @GetMapping("/searchProfessional")
    public ResponseEntity<List<Person>> searchProfessional(@RequestHeader(name="name",required = false) String name,
                                                     @RequestHeader(name="Authorization") String token){
        List<Person>professionalList=personService.searchProfessional(name);
        return ResponseEntity.ok(professionalList);
    }
    @PostMapping("/updateUser")
    public ResponseEntity<Person> updateUser(@RequestBody User user,
                                             @RequestHeader(name="Authorization") String token){
        common.personUpdateRequestCheck(user,token,Role.USER);
        Person person=common.createUpdatedUser(user);;
        Person userResponse = personService.updatePerson(person);
        if(person==null)
            throw new ApiRequestException("User not updated");
        return ResponseEntity.ok(userResponse);
    }

}
