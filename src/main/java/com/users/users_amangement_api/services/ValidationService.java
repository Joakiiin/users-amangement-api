package com.users.users_amangement_api.services;
import org.springframework.stereotype.Service;
import com.users.users_amangement_api.contracts.IValidationService;
import java.util.regex.Pattern;

@Service
public class ValidationService implements IValidationService {
    private static final Pattern TAX_ID_PATTER = Pattern.compile("^[A-ZÑ&]{4}[0-9]{6}[A-Z0-9]{3}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^(\\d\\s*){10}$");
    @Override
    public boolean isValidTaxId(String taxId) {        
        return taxId != null && TAX_ID_PATTER.matcher(taxId).matches();
    }

    @Override
    public boolean isValidPhoneNumber(String phone) {
        if (phone == null) return false;
        String digits = phone.replaceAll("\\D+", "");
        return digits.length() == 10 && PHONE_PATTERN.matcher(digits).matches();
    }
}
