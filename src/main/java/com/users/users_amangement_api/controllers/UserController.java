package com.users.users_amangement_api.controllers;

import com.users.users_amangement_api.models.dtos.*;
import com.users.users_amangement_api.models.User;
import com.users.users_amangement_api.contracts.IUserService;
import com.users.users_amangement_api.contracts.IMapperService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import jakarta.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final IUserService userService;
    private final IMapperService mapperService;

    public UserController(IUserService userService, IMapperService mapperService) {
        this.userService = userService;
        this.mapperService = mapperService;
    }

    @GetMapping
    public ResponseEntity<?> getUsers(@RequestParam(required = false) String filter,
            @RequestParam(required = false) String sortedBy) {
        try {
            List<User> users = userService.getAllUsers(filter, sortedBy);
            List<UserResponseDto> response = mapperService.mapToResponseDto(users);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Internal server error"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable UUID id) {
        try {
            User user = userService.getUserById(id);
            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            UserResponseDto response = mapperService.mapToResponseDto(user);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Internal server error"));
        }
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDto dto) {

        try {
            User user = mapperService.mapToEntity(dto);
            User createdUser = userService.createUser(user);
            UserResponseDto response = mapperService.mapToResponseDto(createdUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Internal server error"));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable UUID id, @Valid @RequestBody UpdateUserDto dto) {
        try {
            User existing = userService.getUserById(id);
            if (existing == null) {
                return ResponseEntity.notFound().build();
            }
            User userUpdates = new User();
            mapperService.mapToEntity(dto, userUpdates);
            User updated = userService.updateUser(id, userUpdates, dto.getPassword());
            UserResponseDto response = mapperService.mapToResponseDto(updated);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Internal server error"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
        try {
            boolean deleted = userService.deleteUser(id);
            if (!deleted) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Internal server error"));
        }
    }
}
