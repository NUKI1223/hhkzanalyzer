package org.ngc.hhkzanalyzer.repository;

import org.ngc.hhkzanalyzer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findByVerificationCode(String verificationCode);
    Optional<User> findUserByUsername(String username);

}
