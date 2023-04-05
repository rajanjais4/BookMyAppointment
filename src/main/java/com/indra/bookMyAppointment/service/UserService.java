package com.indra.bookMyAppointment.service;

import com.indra.bookMyAppointment.exception.ApiRequestException;
import com.indra.bookMyAppointment.model.common.Person;
import com.indra.bookMyAppointment.model.user.User;
import com.indra.bookMyAppointment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    MongoTemplate mongoTemplate;
    public List<User> findAll()
    {
        return userRepository.findAll();
    }

    public User findUserByPhoneNumber(String phoneNumber)
    {
        User userResponse = userRepository.findByPhoneNumber(phoneNumber).orElse(null);
        if(userResponse==null)
            System.out.println("UserService.findUserByPhoneNumber - no user found - "+phoneNumber);
        else
            System.out.println("UserService.findUserByPhoneNumber user found - "+phoneNumber);
        return userResponse;
    }

    public User saveNewUser(User user){
        System.out.println("user to save - "+user.getName());
        if(findUserByPhoneNumber(user.getPhoneNumber())!=null)
        {
            String msg="Phone number already exists - "+user.getPhoneNumber();
            System.out.println(msg);
            throw new ApiRequestException(msg);
        }
        if(userRepository.findByEmail(user.getEmail())!=null)
        {
            String msg="Email already exists - "+user.getEmail();
            System.out.println(msg);
            throw new ApiRequestException(msg);
        }
        System.out.println("Saving New User");
        String id=getUserId(user);
        user.set_id(id);
        user.setRole(Person.Role.USER);
        return userRepository.save(user);
    }
    public User updateUser(User user){
        System.out.println("user to update - "+user.getPhoneNumber());
        if(findUserByPhoneNumber(user.getPhoneNumber())==null)
        {
            String msg="User not found - "+user.getPhoneNumber();
            System.out.println(msg);
            throw new ApiRequestException(msg);
        }
        System.out.println("Updating User");
        String id=getUserId(user);
        user.set_id(id);
        user.setRole(Person.Role.USER);
        return userRepository.save(user);
    }
    private String getUserId(User user) {
        return "U_"+user.getPhoneNumber();
    }
}
