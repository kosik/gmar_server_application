package by.gmar.security;

import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author s.kosik
 */
public class Blowfish {

    private static final Logger LOGGER = LoggerFactory.getLogger(Blowfish.class);
    private static final String fileName = "secret.key";
    private static SecretKey key;
    private static Cipher desCipher;

    private void Blowfish() throws Exception {
        KeyGenerator keygen = KeyGenerator.getInstance("DES");
        key = keygen.generateKey();
        desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
    }

    public String decrypt(final byte[] in) throws Exception {
        try {
            if (null == desCipher) {
                LOGGER.error("Cipher is null");
            }
            desCipher.init(Cipher.DECRYPT_MODE, key);
            return new String(desCipher.doFinal(in));
        } catch (Exception e) {
            throw new Exception("Cannnot decrypt string:" + in + "\n" + e);
        }
    }

    public byte[] encrypt(final String in) {
        byte[] ciphertext = null;
        try {
            if (null == desCipher) {
                LOGGER.error("desCipher is null");
            } else {
                desCipher.init(Cipher.ENCRYPT_MODE, key);
                LOGGER.info("encryp chipper does successfuly initialed");
            }

            byte[] cleartext = in.getBytes();
            ciphertext = desCipher.doFinal(cleartext);

        } catch (Exception e) {
            LOGGER.error("Cannnot encrypt string: " + in);
        }
        return ciphertext;
    }

    public void generateKey() throws Exception {
        KeyGenerator kg;
        try {
            kg = KeyGenerator.getInstance("Blowfish");
            SecretKey key = kg.generateKey();
//    		saveGeneratedKey(key);
        } catch (NoSuchAlgorithmException ignore) {
            throw new Exception("Cannnot generate key for the  DES algorithm.");
        }
    }

}
