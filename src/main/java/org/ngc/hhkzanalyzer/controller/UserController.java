package org.ngc.hhkzanalyzer.controller;

import org.ngc.hhkzanalyzer.model.User;
import org.ngc.hhkzanalyzer.services.FileStorageService;
import org.ngc.hhkzanalyzer.services.PostService;
import org.ngc.hhkzanalyzer.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Controller
public class UserController {
    private final UserService userService;
    private final PostService postService;
    private final FileStorageService fileStorageService;

    private static final String UPLOAD_DIR = "uploads/";


    public UserController(UserService userService, PostService postService, FileStorageService fileStorageService) {
        this.userService = userService;
        this.postService = postService;
        this.fileStorageService = fileStorageService;
    }
    @GetMapping("/user")
    public String getCurrentUser(Model model) {
        User user = userService.getCurrentUser();

        model.addAttribute("user", user);

        return "profile";
    }

    @GetMapping("/{username}")
    public String getUser(@PathVariable String username,Model model) {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User currentUser = userService.getCurrentUser();

        boolean isOwnProfile = currentUser.getUsername().equals(username);


        model.addAttribute("user", user);
        model.addAttribute("isOwnProfile", isOwnProfile);
        model.addAttribute("posts", postService.getPostsByUsername(username));
        return isOwnProfile ? "profile" : "profile-other";
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);

    }
    @PostMapping("/upload-avatar")
    public String uploadAvatar(@RequestParam("avatar") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file");
            return "redirect:/users/me";
        }
        try{
            String fileName = fileStorageService.createFileName(file);
            Path path = Paths.get(UPLOAD_DIR + fileName);
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = (User) authentication.getPrincipal();
            currentUser.setAvatarImage("/" + UPLOAD_DIR + fileName);
            userService.save(currentUser);
            redirectAttributes.addFlashAttribute("message", "Avatar uploaded successfully.");

        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Failed to upload avatar.");
        }
        return "redirect:/user";
    }


}
