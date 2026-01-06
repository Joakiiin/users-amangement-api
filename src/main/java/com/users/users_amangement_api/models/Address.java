package com.users.users_amangement_api.models;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    private Integer id;
    private String name;
    private String street;
    private String countryCode;
}
