package com.users.users_amangement_api.helpers;
import com.users.users_amangement_api.models.User;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;
public class SortHelper {
    public static List<User> sortUsers(List<User> users, String sortedBy) {
        if (sortedBy == null || sortedBy.trim().isEmpty())
            return users;
        Comparator<User> comparator = switch(sortedBy.trim().toLowerCase()){
            case "email" -> Comparator.comparing(User::getEmail);
            case "name" -> Comparator.comparing(User::getName);
            case "phone" -> Comparator.comparing(User::getPhone);
            case "tax_id", "taxid" -> Comparator.comparing(User::getTaxId);
            case "created_at", "createdat" -> Comparator.comparing(User::getCreatedAt);
            case "id" -> Comparator.comparing(User::getId);
            default ->Comparator.comparing(User::getId);
        };
        return users.stream()
        .sorted(comparator)
        .collect(Collectors.toList());
    }
}
