

package com.berenberg.library.security.encryption;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.AEADBadTagException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncryptDecrypt {
    private static final Logger log = LoggerFactory.getLogger(EncryptDecrypt.class);
    public static final int AES_KEY_SIZE = 256;
    public static final int GCM_NONCE_LENGTH = 12;
    public static final int GCM_TAG_LENGTH = 16;

    public EncryptDecrypt() {
    }

    public static String encrypt(String entry) {
        String noncebte = "";
        String encodedKey = "";

        try {
            byte[] input = entry.getBytes();
            SecureRandom random = SecureRandom.getInstanceStrong();
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256, random);
            SecretKey key = keyGen.generateKey();
            encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding", "SunJCE");
            byte[] nonce = new byte[12];
            random.nextBytes(nonce);
            noncebte = Base64.getEncoder().encodeToString(nonce);
            GCMParameterSpec spec = new GCMParameterSpec(128, nonce);
            cipher.init(1, key, spec);
            byte[] cipherText = cipher.doFinal(input);
            entry = Base64.getEncoder().encodeToString(cipherText);
        } catch (Exception var11) {
            log.error(var11.getMessage(), var11);
        }

        String ent = entry + "::" + noncebte + "::" + encodedKey;
        ByteBuffer byteBuffer = ByteBuffer.allocate(ent.getBytes().length);
        byteBuffer.put(ent.getBytes());
        byte[] cipherMessage = byteBuffer.array();
        return Base64.getEncoder().encodeToString(cipherMessage);
    }

    public static String decrypt(String input) {
        try {
            ByteBuffer byteBuffer = ByteBuffer.wrap(Base64.getDecoder().decode(input));
            byte[] full = new byte[byteBuffer.remaining()];
            byteBuffer.get(full);
            String[] split = (new String(full, StandardCharsets.UTF_8)).split("::");
            byte[] bytes = Base64.getDecoder().decode(split[0]);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding", "SunJCE");
            //byte[] decode = Base64.getDecoder().decode(split[1]);
          //  String algo = Base64.getEncoder().encodeToString(decode);
            GCMParameterSpec spec = new GCMParameterSpec(128, Base64.getDecoder().decode(split[1]));
            byte[] decodedKey = Base64.getDecoder().decode(split[2]);
            SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
            cipher.init(2, originalKey, spec);

            try {
                byte[] plainText = cipher.doFinal(bytes);
                String ss = new String(plainText, StandardCharsets.UTF_8);
                input = ss;
            } catch (AEADBadTagException var13) {
                log.error(var13.getMessage(), var13);
            }
        } catch (Exception var14) {
            log.error(var14.getMessage(), var14);
        }

        return input;
    }

    public static void main(String[] args) {
       // System.out.println(encrypt("jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS_LIST =(ADDRESS =(PROTOCOL=TCP)(HOST=localhost)(PORT=32769)))(CONNECT_DATA=(SID=EE)(SERVER=DEDICATED)))"));
        System.out.println(decrypt("z4AwjJecGZs9NUN7cNOrnQ=="));
        //System.out.println(decrypt("dGdwVnVzMHk2cDdPSGI3TVJ4cWtRekxDQUlyYU42a1h3K3ByclE2TlY1b0craERFcGVxbFJSVnhXb3JJQVY0VTRiMDRZelNSTVBqdk1zZjkySEEwWDVpVlhMbEQ2YUVKbUdBMUYzek5iVE44Z3Q0Y1V1ckZ2NUpmdGNFWHowbDU2ZzVJWFhPTmt4UlpVUWUxK3NJNFpndDdpaGJ1QzBrcytyWU1NWjRXb00rOFNsY2I2YVNkcnNWODdsYmJBVEJUOjpmeTVpcE5XNGJLcVVQekhVOjpIOTM5NGZWSDM2WEZVM0k4NlpaU0lOQ1RCbTdBNGJNK3FvclBycGlCNmlRPQ=="));
    
        //System.out.println(encrypt("jdbc:oracle:thin:@//adc-rac-esbfc-scan:1521/srvesb.ecobank.group"));
       // System.out.println(encrypt("03b7f1593dafecb7be7bd58e9e4bd1b1cd1a3d759f95ede1795b4bd0d645d3fa2482329fb101fd770b93d36a6ca394dbc0d5d5b0bb228eda57e56c017020141f"));
    }
}
