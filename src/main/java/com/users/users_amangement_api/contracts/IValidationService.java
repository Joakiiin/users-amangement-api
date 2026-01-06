package com.users.users_amangement_api.contracts;

public interface IValidationService {
    boolean isValidTaxId(String taxId);
    boolean isValidPhoneNumber(String phone);
}
