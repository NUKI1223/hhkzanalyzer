package org.ngc.hhkzanalyzer.controller;

import org.ngc.hhkzanalyzer.dto.LoginUserDTO;
import org.ngc.hhkzanalyzer.dto.RegisterUserDTO;
import org.ngc.hhkzanalyzer.dto.VerifyUserDTO;
import org.ngc.hhkzanalyzer.model.User;
import org.ngc.hhkzanalyzer.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller
public class AuthenticationController {


    private final AuthenticationService authenticationService;

    public AuthenticationController(final AuthenticationService authenticationService) {

        this.authenticationService = authenticationService;
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new RegisterUserDTO());
        return "register";
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDTO registerUserDTO) {
        User user = authenticationService.signUp(registerUserDTO);
        return ResponseEntity.ok(user);
    }
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationController.class);

    @GetMapping(value = "/login")
    public String login() {
        LOG.info("/login");

        LOG.info("Return login");

        //return login.html located in /resources/templates
        return "login";
    }


    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody VerifyUserDTO verifyUserDTO) {
        try {
            authenticationService.verifyUser(verifyUserDTO);
            return ResponseEntity.ok("Account verified successfully");
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/resend")
    public ResponseEntity<?> resend(@RequestParam String email) {
        try {
            authenticationService.resendVerificationCode(email);
            return ResponseEntity.ok("Code resend successfully");
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
