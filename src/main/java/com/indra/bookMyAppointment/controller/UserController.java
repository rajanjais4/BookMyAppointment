package com.indra.bookMyAppointment.controller;

import com.indra.bookMyAppointment.auth.AuthenticationResponse;
import com.indra.bookMyAppointment.config.JwtService;
import com.indra.bookMyAppointment.exception.ApiRequestException;
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
    @Autowired
    JwtService jwtService;


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
    public ResponseEntity<User> getUsersByPhoneNumber(@RequestParam("phoneNumber")String phoneNumber,
                                                      @RequestHeader (name="Authorization") String token)
    {
        String userAuthPhoneNumber=getUserPhoneNumberByToken(token);
        if(!phoneNumber.equals(userAuthPhoneNumber))
            throw new ApiRequestException("user- "+userAuthPhoneNumber+" querying for another user");
        User user=userService.findUserByPhoneNumber(phoneNumber);
        if(user==null)
            throw new ApiRequestException("User not found");
        return ResponseEntity.ok(user);
    }
    @GetMapping("/getUserByAuthToken")
    public ResponseEntity<User> getUsersByAuth(@RequestHeader (name="Authorization") String token)
    {
        String userAuthPhoneNumber=getUserPhoneNumberByToken(token);
        User user=userService.findUserByPhoneNumber(userAuthPhoneNumber);
        if(user==null)
            throw new ApiRequestException("User not found");
        return ResponseEntity.ok(user);
    }

    @PostMapping("/updateUser")
    public ResponseEntity<User> updateUser(@RequestBody User user,
                                           @RequestHeader (name="Authorization") String token){
        String userAuthPhoneNumber=getUserPhoneNumberByToken(token);
        if(user.getPhoneNumber()!=userAuthPhoneNumber)
            throw new ApiRequestException("user- "+userAuthPhoneNumber+" trying to update details of another user");
        User userResponse =userService.updateUser(user);
        if(user==null)
            throw new ApiRequestException("User not updated");
        return ResponseEntity.ok(userResponse);
    }
    private String getUserPhoneNumberByToken(String token) {
        System.out.println("token- "+token);
        String jwt=token.substring(7);;
        String userNumber= jwtService.extractUsername(jwt);
        System.out.println("userNumber- "+userNumber);
        return userNumber;
    }

}
