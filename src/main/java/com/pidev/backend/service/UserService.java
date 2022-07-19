package com.pidev.backend.service;

import com.pidev.backend.repos.RoleRepository;
import com.pidev.backend.entities.Role;
import com.pidev.backend.entities.User;
import com.pidev.backend.repos.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userDao;

    @Autowired
    private RoleRepository roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailSenderService senderService;
    public void initRoleAndUser() {

        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role");
        roleDao.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("Default role for newly created record");
        roleDao.save(userRole);

        User adminUser = new User();
        adminUser.setUserName("admin123");
        adminUser.setUserPassword(getEncodedPassword("admin@pass"));
        adminUser.setUserFirstName("admin");
        adminUser.setUserLastName("admin");
        adminUser.setDisabled(false);
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userDao.save(adminUser);

//        User user = new User();
//        user.setUserName("raj123");
//        user.setUserPassword(getEncodedPassword("raj@123"));
//        user.setUserFirstName("raj");
//        user.setUserLastName("sharma");
//        Set<Role> userRoles = new HashSet<>();
//        userRoles.add(userRole);
//        user.setRole(userRoles);
//        userDao.save(user);
    }

    public User registerNewUser(User user) {
        Role role = roleDao.findById("User").get();
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRole(userRoles);
        user.setUserPassword(getEncodedPassword(user.getUserPassword()));

        return userDao.save(user);
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public User getUser(String username){
       return userDao.findUserByUserName(username);
    }
    public List<User> getLoggedUser(){
       List<User>users = userDao.findUsersByIsLogged(true);
       return users;
    }
    public List<User> getAllUsers(){
//        return userDao.findAll();
        return userDao.findUsersByRole(roleDao.findByRoleName("User"));
    }
    public void deleteUser(String username){
         userDao.delete(userDao.findUserByUserName(username));
    }
    public User updateUser(User user){
        User oldUser = userDao.findUserByUserName(user.getUserName());
        user.setUserName(oldUser.getUserName());
        user.setRole(oldUser.getRole());
        user.setLogged(oldUser.isLogged());
        user.setConnectionTime(oldUser.getConnectionTime());
        user.setLastTimeConnected(oldUser.getLastTimeConnected());
        if (user.getUserPassword()!=null && user.getUserPassword()!="")
        {
            user.setUserPassword(getEncodedPassword(user.getUserPassword()));
        }
        else {
            user.setUserPassword(oldUser.getUserPassword());
        }
        if (user.getUserName()==null){
            user.setUserName(oldUser.getUserName());
        }
        if (user.getUserFirstName()==null){
            user.setUserFirstName(oldUser.getUserFirstName());
        }
        if (user.getUserLastName()==null){
            user.setUserLastName(oldUser.getUserLastName());
        }
        if (user.getEmail()==null || user.getEmail()==""){
            user.setEmail(oldUser.getEmail());
        }
        if (user.getPhone()==null || user.getEmail()=="")
        {
            user.setPhone(oldUser.getPhone());
        }
        if (user.getPhoto()==null ||user.getPhoto()=="")
        {
            user.setPhoto(oldUser.getPhoto());
        }
        return userDao.save(user);


    }

    public String forgetPassword(String username){
        User user = userDao.findUserByUserName(username);
        String newpass=user.getConnectionTime()+username+user.getLastTimeConnected();
        user.setUserPassword(newpass);
        updateUser(user);
        senderService.sendSimpleEmail(user.getEmail(),
                "Votre mot de passe est: "+newpass,
                "reset password");
        System.out.println("email sent");
        return (newpass);
    }
    public boolean changePassword(String username,String oldPassword,String newPassword){
        User user = userDao.findUserByUserName(username);
        String encodedPassword = getEncodedPassword(oldPassword);
        log.info(encodedPassword);
        if (passwordEncoder.matches(oldPassword,user.getUserPassword())) {
            user.setUserPassword(newPassword);
            updateUser(user);
            return true;
        }
        else return false;
    }

}
