package com.users.users_amangement_api.contracts;

public interface IEncryptionService {
    String encrypt(String plainText);
    String decrypt(String cipherText);
} 
