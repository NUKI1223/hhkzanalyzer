package org.ngc.hhkzanalyzer.controller;

import org.ngc.hhkzanalyzer.model.Post;
import org.ngc.hhkzanalyzer.model.PostImage;
import org.ngc.hhkzanalyzer.model.User;
import org.ngc.hhkzanalyzer.services.FileStorageService;
import org.ngc.hhkzanalyzer.services.PostService;
import org.ngc.hhkzanalyzer.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {
    private final PostService postService;
    private final UserService userService;
    private final FileStorageService fileStorageService;

    @Value("${upload.path}")
    private String uploadPath;
    public PostController(PostService postService, UserService userService, FileStorageService fileStorageService) {
        this.postService = postService;
        this.userService = userService;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/post/create")
    public String createPost(@RequestParam String title,
                             @RequestParam String content,
                             @RequestParam("images") MultipartFile[] files,
                             RedirectAttributes redirectAttributes) {
        User user =  userService.getCurrentUser();
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setUser(user);
        List<PostImage> postImages = new ArrayList<>();

        for (MultipartFile file : files){
            if (!file.isEmpty() && file != null){
                try {
                String uniqueFileName = System.currentTimeMillis() + "_" + fileStorageService.createFileName(file);
                Path filePath = Paths.get(uploadPath, uniqueFileName);
                Files.copy(file.getInputStream(), filePath);
                PostImage postImage = new PostImage();
                postImage.setImageUrl("/uploads/" + uniqueFileName);
                postImage.setPost(post);
                postImages.add(postImage);
                } catch (IOException e) {
                    e.printStackTrace();
                    redirectAttributes.addFlashAttribute("error", "Failed to upload image");
                    return "redirect:/" + user.getUsername();
                }
            }
        }
        post.setImages(postImages);
        postService.save(post);
        redirectAttributes.addFlashAttribute("message", "Post created successfully");
        return "redirect:/" + user.getUsername();

    }
    @GetMapping("/feed")
    public String viewFeed(Model model) {
        model.addAttribute("posts", postService.getAllPosts());
        return "post-feed";
    }
}
