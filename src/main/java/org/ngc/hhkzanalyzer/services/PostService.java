package org.ngc.hhkzanalyzer.services;

import org.ngc.hhkzanalyzer.model.Post;
import org.ngc.hhkzanalyzer.model.User;
import org.ngc.hhkzanalyzer.repository.PostsRepository;
import org.ngc.hhkzanalyzer.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PostService {
    private final PostsRepository postsRepository;
    private final UserRepository userRepository;

    public PostService(PostsRepository postsRepository, UserRepository userRepository) {
        this.postsRepository = postsRepository;
        this.userRepository = userRepository;
    }

    public List<Post> getAllPosts() {
        return postsRepository.findAll();
    }
    public Post getPostById(Long id) {
        return postsRepository.findById(id).orElse(null);
    }

    public List<Post> getPostsByUserEmail(String email) {
        return userRepository.findUserByEmail(email)
                .map(user -> postsRepository.findByUserId(user.getId()))
                .orElse(Collections.emptyList());
    }
    public List<Post> getPostsByUsername(String username) {
        return userRepository.findUserByUsername(username)
                .map(user -> postsRepository.findByUserId(user.getId()))
                .orElse(Collections.emptyList());
    }

    public void save(Post post) {
        postsRepository.save(post);
    }
}
