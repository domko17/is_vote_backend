package com.tuke.fei.kpi.isvote.modules.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tuke.fei.kpi.isvote.modules.auth.dto.*;
import com.tuke.fei.kpi.isvote.modules.auth.service.AuthService;
import com.tuke.fei.kpi.isvote.modules.common.dto.UserDto;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping(path = "/current-user")
    public UserDto getCurrentUser() {
        return authService.getCurrentUser();
    }

    @PutMapping(path = "/change-password")
    public TokenResponse changePassword(@Valid @RequestBody PasswordChangeDto passwordChangeDto) {
        return authService.changePassword(passwordChangeDto);
    }

    @PostMapping(path = "/login/credentials")
    public TokenResponse logInViaCredentials(@Valid @RequestBody CredentialsDto credentials) {
        return authService.logInViaCredentials(credentials);
    }

    @PostMapping(path = "/login/refresh-token")
    public TokenResponse logInViaRefreshToken(@Valid @RequestBody RefreshTokenDto refreshToken) {
        return authService.logInViaRefreshToken(refreshToken);
    }

    @PostMapping(path = "/login/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestParam("email") String mail) {
        log.info("About to reset password for email {}", mail);
        authService.resetPassword(mail);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login/create-user")
    public UserDto createUser(@Valid @RequestBody CreateUserDto createUserDto) {
        return authService.createUser(createUserDto);
    }
}
