package com.nicholasallum.microbank.auth;

public record AuthResponse(Long userId, String fullName, String email, String message) {}
