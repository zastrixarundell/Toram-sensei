package com.github.zastrixarundell.torambot.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

public class AESHelper
{

    private final String algorithm = "AES";
    private Key key;

    public AESHelper(String token)
    {
        String password = token.substring(0, 32);
        key = new SecretKeySpec(password.getBytes(), algorithm);
    }

    public String encryptData(String data) throws Exception
    {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decryptData(String data) throws Exception
    {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedBaseValues = Base64.getDecoder().decode(data);
        byte[] deciphered = cipher.doFinal(decodedBaseValues);
        return new String(deciphered);
    }



}
