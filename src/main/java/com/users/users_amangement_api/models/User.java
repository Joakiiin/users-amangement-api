package com.users.users_amangement_api.models;

import lombok.*;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private UUID id;
    private String email;
    private String name;
    private String phone;
    private String password;
    private String taxId;
    @Builder.Default
    private String createdAt = getCurrentMadagascarTime();
    @Builder.Default
    private List<Address> addresses = new ArrayList<>();

    private static String getCurrentMadagascarTime(){
        return LocalDateTime.now(ZoneId.of("Indian/Antananarivo"))
        .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss:SSSS"));
    }
}
