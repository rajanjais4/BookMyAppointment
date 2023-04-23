package com.indra.bookMyAppointment.common;

import com.indra.bookMyAppointment.config.JwtService;
import com.indra.bookMyAppointment.exception.ApiRequestException;
import com.indra.bookMyAppointment.model.common.Person;
import com.indra.bookMyAppointment.model.common.Role;
import com.indra.bookMyAppointment.model.professional.Professional;
import com.indra.bookMyAppointment.model.user.User;
import com.indra.bookMyAppointment.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Common {
    @Autowired
    JwtService jwtService;
    @Autowired
    PersonRepository personRepository;
    public String getPersonPhoneNumberByToken(String token) {
        String jwt=token.substring(7);;
        String userNumber= jwtService.extractUsername(jwt);
        System.out.println("userNumber- "+userNumber);
        return userNumber;
    }
    public Role getPersonRoleByToken(String token) {
        System.out.println("token- "+token);
        String jwt=token.substring(7);;
        Role role= jwtService.extractRole(jwt);
        System.out.println("Role- "+role.name());
        return role;
    }
    public Person createUpdatedUser(User user) {
        User updatedUser= (User) personRepository.findByPhoneNumber(user.getPhoneNumber()).orElse(null);
        if(user.getAddress()!=null){
            updatedUser.setAddress(user.getAddress());
        }
        if(user.getName()!=null){
            updatedUser.setName(user.getName());
        }
        return updatedUser;
    }
    public Person createUpdatedProfessional(Professional professional) {
        Professional updatedProfessional= (Professional) personRepository.findByPhoneNumber(professional.getPhoneNumber()).orElse(null);
        if(professional.getAddress()!=null){
            updatedProfessional.setAddress(professional.getAddress());
        }
        if(professional.getName()!=null){
            updatedProfessional.setName(professional.getName());
        }
        if(professional.getProfession()!=null){
            updatedProfessional.setProfession(professional.getProfession());
        }
        if(professional.getSummary()!=null){
            updatedProfessional.setSummary(professional.getSummary());
        }
        if(professional.getQualification()!=null){
            updatedProfessional.setQualification(professional.getQualification());
        }
        if(professional.getDescription()!=null){
            updatedProfessional.setDescription(professional.getDescription());
        }
        if(professional.getExperience()!=null){
            updatedProfessional.setExperience(professional.getExperience());
        }
        return updatedProfessional;
    }
// Check and confirm that right persion with right role updating its profile
    public void personUpdateRequestCheck(Person person, String token, Role role) {
        if(person.getPhoneNumber()==null)
            throw new ApiRequestException("phone number not provided not found - ");
        String personAuthPhoneNumber= getPersonPhoneNumberByToken(token);
        Role personAuthRole= getPersonRoleByToken(token);
        if(!person.getPhoneNumber().equals(personAuthPhoneNumber))
            throw new ApiRequestException("Person - "+personAuthPhoneNumber+" trying to update details of another Person");
        if(!role.equals(personAuthRole))
            throw new ApiRequestException("Person's role in auth-token and called api role not matching");
        if(person.getRole()!=null&&!person.getRole().equals(personAuthRole))
            throw new ApiRequestException("Person role in auth-token and role provided are not matching");
        if(personRepository.findByPhoneNumber(person.getPhoneNumber()).orElse(null)==null)
            throw new ApiRequestException("Person not found - "+person.getPhoneNumber());
    }
    public String createPersonId(Person person) {
        Role role=person.getRole();
        if(role==Role.USER)
            return "U_"+person.getPhoneNumber();
        else if(role==Role.PROFESSIONAL)
            return "P_"+person.getPhoneNumber();
        else
            return "A_"+person.getPhoneNumber();
    }
}
