package org.ngc.hhkzanalyzer.repository;

import org.ngc.hhkzanalyzer.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostsRepository extends JpaRepository<Post, Long> {

    List<Post> findByUserId(Long userId);
}
