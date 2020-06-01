package model.crypto;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.AEADBadTagException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class AESGCM implements CryptoCipher {

    private static final int IV_SIZE = 12;
    // not an error, in reality the iv size is the nonce.
    private static final int IV_SIZE_BIT = 128;
    private static final int KEY_SIZE = 32;

    private Cipher cipher;
    private SecretKeySpec aesKey;
    private byte[] associatedData;

    public AESGCM() {
        try {
            this.cipher = Cipher.getInstance("AES/GCM/NoPadding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            System.out.println("Error building AES-GCM object: " + e.toString());
        }
    }

    @Override
    public final byte[] encrypt(final byte[] plaintext, final byte[] iv) {
        GCMParameterSpec ivParameterSpec = new GCMParameterSpec(IV_SIZE_BIT, iv);
        try {
            this.cipher.init(Cipher.ENCRYPT_MODE, this.aesKey, ivParameterSpec);
            this.updateAAD();
            return this.cipher.doFinal(plaintext);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException
                | BadPaddingException e) {
            System.out.println("Error AES-GCM encryption: " + e.toString());
        }
        return null;
    }

    @Override
    public final byte[] decrypt(final byte[] ciphertext, final byte[] iv) throws AEADBadTagException {
        GCMParameterSpec ivParameterSpec = new GCMParameterSpec(IV_SIZE_BIT, iv);
        try {
            this.cipher.init(Cipher.DECRYPT_MODE, this.aesKey, ivParameterSpec);
            this.updateAAD();
            return this.cipher.doFinal(ciphertext);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException e) {
            System.out.println("Error " + this.getClass() + " this shouldn't happen: " + e.toString());
            return null;
        } catch (BadPaddingException e) {
            throw new AEADBadTagException("Error " + this.getClass() + " tag mismatch");
        }
    }

    @Override
    public final void setKey(final byte[] key) {
        this.aesKey = new SecretKeySpec(key, "AES");
    }

    @Override
    public final int getIVSize() {
        return AESGCM.IV_SIZE;
    }

    @Override
    public final int getKeySize() {
        return AESGCM.KEY_SIZE;
    }

    @Override
    public final void updateAssociatedData(final byte[] data) {
        this.associatedData = Arrays.copyOf(data, data.length);
    }

    private void updateAAD() {
        if (this.associatedData != null) {
            this.cipher.updateAAD(this.associatedData);
        }
    }
}
