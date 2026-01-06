package com.users.users_amangement_api.services;

import org.springframework.stereotype.Service;
import com.users.users_amangement_api.models.dtos.*;
import com.users.users_amangement_api.contracts.IEncryptionService;
import com.users.users_amangement_api.contracts.IMapperService;
import com.users.users_amangement_api.models.Address;
import com.users.users_amangement_api.models.User;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MapperService implements IMapperService {
    private final IEncryptionService encryptionService;

    public MapperService(IEncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    @Override
    public User mapToEntity(CreateUserDto dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setPhone(dto.getPhone());
        user.setPassword(dto.getPassword());
        user.setTaxId(dto.getTaxId());
        if (dto.getAddresses() != null) {
            List<Address> addresses = dto.getAddresses().stream()
                    .map(this::mapAddressDtoToEntity)
                    .collect(Collectors.toList());
            user.setAddresses(addresses);
        }
        return user;
    }

    @Override
    public void mapToEntity(UpdateUserDto dto, User user) {
        if (dto.getEmail() != null)
            user.setEmail(dto.getEmail());
        if (dto.getName() != null)
            user.setName(dto.getName());
        if (dto.getPhone() != null)
            user.setPhone(dto.getPhone());
        if (dto.getPassword() != null)
            user.setPassword(dto.getPassword());
        if (dto.getTaxId() != null)
            user.setTaxId(dto.getTaxId());
        if (dto.getAddresses() != null) {
            List<Address> addresses = dto.getAddresses().stream()
                    .map(this::mapAddressDtoToEntity)
                    .collect(Collectors.toList());
            user.setAddresses(addresses);
        }
    }

    @Override
    public UserResponseDto mapToResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setPhone(user.getPhone());
        dto.setTaxId(user.getTaxId());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setAddresses(user.getAddresses());
        return dto;
    }

    @Override
    public List<UserResponseDto> mapToResponseDto(List<User> users) {
        return users.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    private Address mapAddressDtoToEntity(AddressDto dto) {
        Address address = new Address();
        address.setId(dto.getId());
        address.setName(dto.getName());
        address.setStreet(dto.getStreet());
        address.setCountryCode(dto.getCountryCode());
        return address;
    }
}
