package com.users.users_amangement_api.services;
import org.springframework.stereotype.Service;
import com.users.users_amangement_api.contracts.IEncryptionService;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.nio.charset.StandardCharsets;

@Service
public class AesEncryptionService implements IEncryptionService {
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private final SecretKey secretKey;
    private final byte[] iv;

    public AesEncryptionService() {
        String keyString = "1234567891011121314151617181920!";
        String ivString = "123456789101112!";
        this.secretKey = new SecretKeySpec(
                keyString.getBytes(StandardCharsets.UTF_8),
                "AES");
        this.iv = ivString.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String encrypt(String plainText) {
        if (plainText == null || plainText.isEmpty())
            return plainText;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
            byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Error in encrypt", e);
        }
    }

    @Override
    public String decrypt(String cipherText) {
        if (cipherText == null || cipherText.isEmpty())
            return cipherText;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
            byte[] decoded = Base64.getDecoder().decode(cipherText);
            byte[] decrypted = cipher.doFinal(decoded);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Error in decrypt", e);
        }
    }
}
