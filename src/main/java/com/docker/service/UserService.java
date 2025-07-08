package com.docker.service;

import com.docker.model.User;
import com.docker.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    public String CreateUser(User user) {

        if (user.getName() == null || user.getName().isEmpty()) {
            return "name is empty";
        }

        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            return "username is empty";
        }

        if (user.getAddr() == null || user.getAddr().isEmpty()) {
            return "addr is empty";
        }
        Optional<User> user1 = repo.findByUsername(user.getUsername());
        if (user1.isPresent()) {
            return "user already exists";
        }

        user.setRole(List.of("User"));
        repo.save(user);
        return "user saved successfully";


    }

    public String updateUser(User user) {
        Optional<User> existingUserOpt = repo.findByUsername(user.getUsername());
        User existingUser = existingUserOpt.get();
        if (existingUserOpt.isEmpty()) {
            return "User is not present there";
        }
        if (existingUser.getUsername().equals(user.getUsername()) &&
                existingUser.getName().equals(user.getName()) &&
                existingUser.getAddr().equals(user.getAddr())
        ) {
            return "no update made";
        }
        existingUser.setName(user.getName());
        existingUser.setAddr(user.getAddr());
        repo.save(existingUser);
        return "user updated successfully";
    }


    @Transactional
    public String delete(String username) {
        Optional<User> existingUserOpt = repo.findByUsername(username);
        if (existingUserOpt.isEmpty()) return "user does not exist";
        repo.deleteByUsername(username);
        return "user deleted successfully";
    }

}
