package com.users.users_amangement_api.services;

import com.users.users_amangement_api.models.User;
import com.users.users_amangement_api.models.Address;
import com.users.users_amangement_api.contracts.IEncryptionService;
import com.users.users_amangement_api.contracts.IUserService;
import com.users.users_amangement_api.contracts.IValidationService;
import com.users.users_amangement_api.helpers.FilterHelper;
import com.users.users_amangement_api.helpers.SortHelper;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserService implements IUserService {
    private final List<User> users = new ArrayList<>();
    private int nextAddressId = 3;
    private final IEncryptionService encryptionService;
    private final IValidationService validationService;

    public UserService(IEncryptionService encryptionService, IValidationService validationService) {
        this.encryptionService = encryptionService;
        this.validationService = validationService;
        initializeSampleUsers();
    }

    private void initializeSampleUsers() {
        String[] countryCodes = { "UK", "AU", "AG", "AM", "MX", "NZ" };
        int addressCounter = 1;
        int countryCodeIndex = 0;
        String[][] sampleData = {
                { "galindotrejojoaquin@mail.com", "Joaquin Galindo Trejo", "1 55 555 555 55", "Password123",
                        "AARR990101XXX" },
                { "cajadeapagadores@mail.com", "Miguel Galindo Padierna", "2 55 556 556 56", "Password456",
                        "BBRR990101XXX" },
                { "trejoanaisabel@mail.com", "Ana Isabel Trejo Hernandez", "3 55 557 557 57", "Password789",
                        "CCRR990101XXX" } };
        for (String[] data : sampleData) {
            User user = User.builder()
                    .id(UUID.randomUUID())
                    .email(data[0])
                    .name(data[1])
                    .phone(data[2])
                    .password(encryptionService.encrypt(data[3]))
                    .taxId(data[4])
                    .addresses(new ArrayList<>())
                    .build();

            user.getAddresses().add(Address.builder()
                    .id(addressCounter)
                    .name("workaddress")
                    .street("street No." + (addressCounter + 1))
                    .countryCode(countryCodes[countryCodeIndex % countryCodes.length])
                    .build());
                    addressCounter++;
                    countryCodeIndex++;

            user.getAddresses().add(Address.builder()
                    .id(addressCounter)
                    .name("homeaddress")
                    .street("street No." + (addressCounter + 1))
                    .countryCode(countryCodes[countryCodeIndex % countryCodes.length])
                    .build());
                    addressCounter++;
                    countryCodeIndex++;
            users.add(user);
        }
        nextAddressId = addressCounter;
    }

    @Override
    public List<User> getAllUsers(String filter, String sortedBy) {
        List<User> result = new ArrayList<>(users);
        if (filter != null && !filter.trim().isEmpty()) {
            result = FilterHelper.filterUsers(result, filter);
        }
        if (sortedBy != null && !sortedBy.trim().isEmpty()) {
            result = SortHelper.sortUsers(result, sortedBy);
        }
        return result;
    }

    @Override
    public User getUserById(UUID Id) {
        return users.stream()
                .filter(u -> u.getId().equals(Id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User createUser(User user) {
        if (users.stream().anyMatch(u -> u.getTaxId().equalsIgnoreCase(user.getTaxId()))) {
            throw new IllegalArgumentException("Tax Id existing.");
        }
        if (!validationService.isValidTaxId(user.getTaxId())) {
            throw new IllegalArgumentException("Tax Id invalid. Example: BBRR990101XXX");
        }
        if (!validationService.isValidPhoneNumber(user.getPhone())) {
            throw new IllegalArgumentException("Phone invalid. Example: 5526602560");
        }
        user.setPassword(encryptionService.encrypt(user.getPassword()));
        user.setId(UUID.randomUUID());
        if (user.getAddresses() != null) {
            for (Address address : user.getAddresses()) {
                address.setId(nextAddressId++);
            }
        }
        users.add(user);
        return user;
    }

    @Override
    public User updateUser(UUID Id, User userUpdates, String plainPassword) {
        User user = getUserById(Id);
        if (user == null)
            return null;
        if (userUpdates.getEmail() != null)
            user.setEmail(userUpdates.getEmail());
        if (userUpdates.getName() != null)
            user.setName(userUpdates.getName());
        if (userUpdates.getPhone() != null)
            user.setPhone(userUpdates.getPhone());
        if (userUpdates.getTaxId() != null)
            user.setTaxId(userUpdates.getTaxId());
        if (userUpdates.getAddresses() != null)
            user.setAddresses(userUpdates.getAddresses());
        if (plainPassword != null && !plainPassword.trim().isEmpty()) {
            user.setPassword(encryptionService.encrypt(plainPassword));
        }
        return user;
    }

    @Override
    public boolean deleteUser(UUID Id) {
        User user = getUserById(Id);
        if (user == null)
            return false;
        return users.remove(user);
    }
}
