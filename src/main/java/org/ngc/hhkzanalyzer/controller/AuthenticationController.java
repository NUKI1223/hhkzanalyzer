package org.ngc.hhkzanalyzer.controller;

import org.ngc.hhkzanalyzer.dto.LoginUserDTO;
import org.ngc.hhkzanalyzer.dto.RegisterUserDTO;
import org.ngc.hhkzanalyzer.dto.VerifyUserDTO;
import org.ngc.hhkzanalyzer.model.User;
import org.ngc.hhkzanalyzer.responses.LoginResponse;
import org.ngc.hhkzanalyzer.services.AuthenticationService;
import org.ngc.hhkzanalyzer.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(final JwtService jwtService, final AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }


    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDTO registerUserDTO) {
        User user = authenticationService.signUp(registerUserDTO);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserDTO loginUserDTO) throws Exception {
        User user = authenticationService.signIn(loginUserDTO);
        String token = jwtService.generateToken(user);
        LoginResponse loginResponse = new LoginResponse(token, jwtService.getJwtExpiration());
        return ResponseEntity.ok(loginResponse);
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
