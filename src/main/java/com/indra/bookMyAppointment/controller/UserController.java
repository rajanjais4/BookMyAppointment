package com.indra.bookMyAppointment.controller;

import com.indra.bookMyAppointment.model.user.User;
import com.indra.bookMyAppointment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<User> getAllUsers()
    {
        return userService.findAll();
    }

    @GetMapping("/getUserByPhoneNumber")
    public User getUsersByPhoneNumber(@RequestParam("phoneNumber")String phoneNumber)
    {
        return userService.findUserByPhoneNumber(phoneNumber);
    }

    @PostMapping("/postNewUser")
    public User createUser(@RequestBody User user){
        User userResponse =userService.saveNewUser(user);
        return userResponse;
    }

}
