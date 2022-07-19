package com.pidev.backend.repos;
import com.pidev.backend.entities.Role;
import com.pidev.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    public User findUserByUserName(String username);
    public List<User> findUsersByRole(Role role );
    public List<User> findUsersByIsLogged(boolean isLogged);

}
