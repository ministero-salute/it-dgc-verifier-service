package it.interop.dgc.verifier.utils;

import it.interop.dgc.verifier.exceptions.BusinessException;
import java.security.MessageDigest;
import java.util.Base64;

public class StringUtility {

    public static String encodeSHA256(String oggettoDaCodificare) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(oggettoDaCodificare.getBytes());
            return encodeBase64(hash);
        } catch (Exception e) {
            throw new BusinessException("Error in the SHA-256 calculation phase", e);
        }
    }

    public static String encodeBase64(final byte[] input) {
        return Base64.getEncoder().encodeToString(input);
    }
}
