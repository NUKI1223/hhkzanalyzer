package org.ngc.hhkzanalyzer.config;
import org.ngc.hhkzanalyzer.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private final  CustomUserDetailsService customUserDetailsService;



    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.customUserDetailsService = userDetailsService;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Используйте BCrypt для шифрования паролей
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())// Отключаем CSRF (можно включить для веб-приложений)
                .securityContext(context -> context.requireExplicitSave(false))
                .authorizeHttpRequests(authorize -> authorize.requestMatchers("/","/verify", "/signup", "/WEB-INF/**", "/login", "/feed").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login").permitAll().defaultSuccessUrl("/", true))
                .logout(logout -> logout.logoutUrl("/logout").permitAll());

        return http.build(); // Возвращаем конфигурацию
    }
}

