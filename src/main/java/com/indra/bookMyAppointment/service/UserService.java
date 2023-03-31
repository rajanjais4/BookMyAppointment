package com.indra.bookMyAppointment.service;

import com.indra.bookMyAppointment.model.user.User;
import com.indra.bookMyAppointment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    public User saveNewUser(User user){
        if(findUserByPhoneNumber(user.getPhoneNumber())!=null)
        {
            System.out.println("Phone number already exists - "+user.getPhoneNumber());
            return null;
        }
        if(userRepository.findByEmail(user.getEmail())!=null)
        {
            System.out.println("Email already exists - "+user.getEmail());
            return null;
        }
        System.out.println("Saving New User");
        String id=getUserId(user);
        user.set_id(id);
        return userRepository.save(user);
    }
    private String getUserId(User user) {
        return "U_"+user.getPhoneNumber();
    }
}
