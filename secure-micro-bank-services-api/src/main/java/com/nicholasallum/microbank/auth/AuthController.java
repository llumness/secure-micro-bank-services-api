package com.nicholasallum.microbank.auth;

import com.nicholasallum.microbank.common.ApiException;
import com.nicholasallum.microbank.user.BankUser;
import com.nicholasallum.microbank.user.BankUserRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final BankUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(BankUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new ApiException("Email already registered");
        }
        BankUser user = new BankUser();
        user.setFullName(request.fullName());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        BankUser saved = userRepository.save(user);
        return new AuthResponse(saved.getId(), saved.getFullName(), saved.getEmail(), "User registered successfully");
    }
}
