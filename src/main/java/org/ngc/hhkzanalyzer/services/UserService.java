package org.ngc.hhkzanalyzer.services;

import org.ngc.hhkzanalyzer.model.User;
import org.ngc.hhkzanalyzer.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
}
