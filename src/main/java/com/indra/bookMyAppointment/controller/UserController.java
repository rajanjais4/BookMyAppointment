package com.indra.bookMyAppointment.controller;

import com.indra.bookMyAppointment.auth.AuthenticationResponse;
import com.indra.bookMyAppointment.model.user.User;
import com.indra.bookMyAppointment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;


    @ApiIgnore
    @RequestMapping(value="/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers()
    {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/getUserByPhoneNumber")
    public ResponseEntity<User> getUsersByPhoneNumber(@RequestParam("phoneNumber")String phoneNumber)
    {
        User user=userService.findUserByPhoneNumber(phoneNumber);
//        if(user==null)
//            throw new UsernameNotFoundException("User Not Found");
        return ResponseEntity.ok(user);
    }

    @PostMapping("/postNewUser")
    public ResponseEntity<User> createUser(@RequestBody User user){
        User userResponse =userService.saveNewUser(user);
        if(user==null)
            throw new UsernameNotFoundException("User not created");
        return ResponseEntity.ok(userResponse);
    }

}
