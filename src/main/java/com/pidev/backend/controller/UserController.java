package com.pidev.backend.controller;

import com.pidev.backend.entities.User;
import com.pidev.backend.repos.UserRepository;
import com.pidev.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.security.Principal;
import java.util.Date;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    private SimpMessagingTemplate template;
    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }

    @PostMapping({"/registerNewUser"})
    public User registerNewUser(@RequestBody User user) {
        return userService.registerNewUser(user);
    }

    @GetMapping({"/forAdmin"})
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin(){
        return "This URL is only accessible to the admin";
    }

    @GetMapping({"/forUser"})
    @PreAuthorize("hasRole('User')")
    public String forUser(){
        return "This URL is only accessible to the user";
    }
    @GetMapping("/loggedusers")
    public List<User> getLoggedUsers(){
        return userService.getLoggedUser();
    }
    @GetMapping("/users")
    public List<User> getAllUser(){
        return userService.getAllUsers();
    }
    @DeleteMapping("/users/{username}")
    public ResponseEntity deleteUser(@PathVariable String username){
        userService.deleteUser(username);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }
    @GetMapping("/userInfo")
    public User userInfo(Principal principal) {
        return userService.getUser(principal.getName());
    }
    @GetMapping("/login/{username}")
    public void login (@PathVariable String username){
        User user = userService.getUser(username);
        user.setLogged(true);
       user.setLastTimeConnected(new Date());
       user.setConnectionTime(user.getConnectionTime()+1);
        userRepository.save(user);
    }
    @GetMapping("/logout/{username}")
    public void logout (@PathVariable String username){
        User user = userService.getUser(username);
        user.setLogged(false);
        userRepository.save(user);


    }
    @GetMapping("/user/approuve/{user}")
    public void anableUser( @PathVariable String user){
        User userup=userService.getUser(user);
        userup.setDisabled(false);
        userRepository.save(userup);
    }
    @GetMapping("/forgetPassword/{username}")
    public String forgetPassword( @PathVariable String username){
        return userService.forgetPassword(username);
    }
    @GetMapping("/changePassword/{username}/{oldPassword}/{newPassword}")
    public boolean changePassword( @PathVariable String username,@PathVariable String oldPassword,@PathVariable String newPassword){
        return userService.changePassword(username,oldPassword,newPassword);
    }
}
