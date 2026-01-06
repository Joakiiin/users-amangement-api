package com.users.users_amangement_api.contracts;
import java.util.List;
import java.util.UUID;
import com.users.users_amangement_api.models.User;

public interface IUserService {
    List<User> getAllUsers(String filter, String sortedBy);
    User getUserById(UUID Id);
    User createUser(User user);
    User updateUser(UUID Id, User userUpdates, String plainPassword);
    boolean deleteUser(UUID Id);
}
