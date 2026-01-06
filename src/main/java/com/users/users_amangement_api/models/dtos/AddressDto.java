package com.users.users_amangement_api.models.dtos;
import lombok.*;

@Data
public class AddressDto {
    private Integer id;
    private String name;
    private String street;
    private String countryCode;
}
