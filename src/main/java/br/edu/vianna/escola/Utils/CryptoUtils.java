package br.edu.vianna.escola.Utils;

import java.beans.Encoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class CryptoUtils {

    public static String md5(String valor) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");

        md.update(valor.getBytes());
        byte[] bytes = md.digest();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        String generatedHash = sb.toString();
        byte[] hashBytes = md.digest(generatedHash.getBytes());
        String base64EncodedHash = Base64.getEncoder().encodeToString(hashBytes);


        return base64EncodedHash;
    }


    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println( md5("123") );
        System.out.println( md5("a") );
        System.out.println( md5("A") );
    }

}
