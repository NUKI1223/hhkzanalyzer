package org.ngc.hhkzanalyzer.services;

import org.ngc.hhkzanalyzer.model.User;
import org.ngc.hhkzanalyzer.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
        public final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
            List<User> users = new ArrayList<User>();
            userRepository.findAll().forEach(users::add);
            return users;
    }
    public void save(User user) {
        userRepository.save(user);
    }
    public Optional<User> findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
    public Optional<User> findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        return currentUser;
    }

}
