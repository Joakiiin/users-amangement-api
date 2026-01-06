package com.users.users_amangement_api.contracts;
import java.util.List;
import com.users.users_amangement_api.models.User;
import com.users.users_amangement_api.models.dtos.CreateUserDto;
import com.users.users_amangement_api.models.dtos.UpdateUserDto;
import com.users.users_amangement_api.models.dtos.UserResponseDto;

public interface IMapperService {
    User mapToEntity(CreateUserDto dto);
    void mapToEntity(UpdateUserDto dto, User user);
    UserResponseDto mapToResponseDto(User user);
    List<UserResponseDto> mapToResponseDto(List<User> users);
}
