package com.users.users_amangement_api.models.dtos;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.List;

@Data
public class CreateUserDto {
    @NotBlank @Email
    private String email;
    @NotBlank @Size(min = 2)
    private String name;
    @NotBlank
    private String phone;
    @NotBlank @Size(min = 6)
    private String password;
    @NotBlank
    private String taxId;
    private List<AddressDto> addresses;
}
