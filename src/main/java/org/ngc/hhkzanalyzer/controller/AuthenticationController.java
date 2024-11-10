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
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationController.class);

    public AuthenticationController(final AuthenticationService authenticationService) {

        this.authenticationService = authenticationService;
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new RegisterUserDTO());
        return "register";
    }

    @PostMapping("/signup")
    public String register(@ModelAttribute RegisterUserDTO registerUserDTO) {
        LOG.info("RegisterUserDTO: {}", registerUserDTO);
        User user = authenticationService.signUp(registerUserDTO);
        return "redirect:/signin";
    }


    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping("/verify")
    public String showVerificationPage(Model model) {
        model.addAttribute("verifyUser", new VerifyUserDTO());
        return "verify";
    }


    @PostMapping("/verify")
    public String verify(@ModelAttribute VerifyUserDTO verifyUserDTO) {
        try {
            authenticationService.verifyUser(verifyUserDTO);
            return "redirect:/signin";
        }
        catch (RuntimeException e) {
            return "redirect:/verify";
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
