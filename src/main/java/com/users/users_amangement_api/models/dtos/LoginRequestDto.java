package com.users.users_amangement_api.models.dtos;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDto {
    @NotBlank
    private String taxId;
    @NotBlank
    private String password;
}
