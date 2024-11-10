package org.ngc.hhkzanalyzer.repository;

import org.ngc.hhkzanalyzer.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Integer> {
    Optional<Topic> findByName(String name);
}
