package com.users.users_amangement_api.helpers;

import com.users.users_amangement_api.models.User;
import java.util.List;
import java.util.stream.Collectors;
import java.util.function.Function;

public class FilterHelper {
    public static List<User> filterUsers(List<User> users, String filter) {
        if (filter == null || filter.trim().isEmpty())
            return users;
        String[] parts = filter.split("\\+");
        if (parts.length != 3)
            return List.of();
        String field = parts[0].toLowerCase();
        String operation = parts[1].toLowerCase();
        String value = parts[2];
        return switch (field) {
            case "email" -> applyFilter(users, User::getEmail, operation, value);
            case "name" -> applyFilter(users, User::getName, operation, value);
            case "phone" -> applyFilter(users, User::getPhone, operation, value);
            case "tax_id", "taxid" -> applyFilter(users, User::getTaxId, operation, value);
            case "created_at", "createdat" -> applyFilter(users, User::getCreatedAt, operation, value);
            case "id" -> applyFilter(users, u -> u.getId().toString(), operation, value);
            default -> List.of();
        };
    }

    private static List<User> applyFilter(List<User> users, Function<User, String> selector, String operation,
            String value) {
        return switch (operation) {
            case "co" -> users.stream()
                    .filter(u -> selector.apply(u).toLowerCase().contains(value.toLowerCase()))
                    .collect(Collectors.toList());
            case "eq" -> users.stream()
                    .filter(u -> selector.apply(u).equalsIgnoreCase(value))
                    .collect(Collectors.toList());
            case "sw" -> users.stream()
                    .filter(u -> selector.apply(u).toLowerCase().startsWith(value.toLowerCase()))
                    .collect(Collectors.toList());
            case "ew" -> users.stream()
                    .filter(u -> selector.apply(u).toLowerCase().endsWith(value.toLowerCase()))
                    .collect(Collectors.toList());
            default -> List.of();
        };
    }
}
