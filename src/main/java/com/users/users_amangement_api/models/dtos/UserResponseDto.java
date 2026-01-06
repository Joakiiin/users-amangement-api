package com.users.users_amangement_api.models.dtos;
import com.users.users_amangement_api.models.Address;
import lombok.Data;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

@Data
public class UserResponseDto {
    private UUID id;
    private String email;
    private String name;
    private String phone;
    private String taxId;
    private String createdAt;
    private List<Address> addresses;
}
