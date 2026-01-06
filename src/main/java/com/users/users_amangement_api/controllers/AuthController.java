package com.users.users_amangement_api.controllers;

import com.users.users_amangement_api.models.dtos.*;
import com.users.users_amangement_api.models.User;
import com.users.users_amangement_api.contracts.IUserService;
import com.users.users_amangement_api.contracts.IMapperService;
import com.users.users_amangement_api.contracts.IEncryptionService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import jakarta.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/login")
public class AuthController {
    private final IUserService userService;
    private final IMapperService mapperService;
    private final IEncryptionService encryptionService;

    public AuthController(IUserService userService, IMapperService mapperService,
            IEncryptionService encryptionService) {
        this.userService = userService;
        this.mapperService = mapperService;
        this.encryptionService = encryptionService;
    }

    @PostMapping
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginRequest) {
        try {
            if (loginRequest.getTaxId() == null || loginRequest.getTaxId().trim().isEmpty()
                    || loginRequest.getPassword() == null || loginRequest.getPassword().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Tax Id and password are required"));
            }
            List<User> allUsers = userService.getAllUsers(null, null);
            User user = allUsers.stream()
                    .filter(u -> u.getTaxId().equalsIgnoreCase(loginRequest.getTaxId()))
                    .findFirst()
                    .orElse(null);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Invalid credentials"));
            }
            String decryptedPassword = encryptionService.decrypt(user.getPassword());
            if (!decryptedPassword.equals(loginRequest.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Invalid credentials"));
            }
            UserResponseDto userResponse = mapperService.mapToResponseDto(user);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Succesful");
            response.put("user", userResponse);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Internal server error"));
        }
    }
}
