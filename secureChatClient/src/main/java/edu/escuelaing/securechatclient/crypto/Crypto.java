/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.securechatclient.crypto;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author diego
 */
public class Crypto {
    
    protected static String DEFAULT_ENCRYPTION_ALGORITHM = "RSA";
    protected static int DEFAULT_ENCRYPTION_KEY_LENGTH = 1024;
    protected static String DEFAULT_TRANSFORMATION = "RSA/ECB/PKCS1Padding";

    protected PublicKey mPublicKey;
    protected PrivateKey mPrivateKey;
    
    protected PublicKey cPublicKey;
    
    
    public void decodeKey(String key) throws NoSuchAlgorithmException, InvalidKeySpecException{
        byte publicKeyData[] = Base64.getDecoder().decode(key);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeyData);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey publicKey = kf.generatePublic(spec);
        cPublicKey=publicKey;
    }
    
    
    
    public PublicKey getPublicKey(){
        return cPublicKey;
    }
    
    public byte[] getPublicKeyAsByteArray(){
        return mPublicKey.getEncoded();
    }
    
    public String getEncodedPublicKey(){
        String encodedKey = Base64.getEncoder().encodeToString(mPublicKey.getEncoded());
        return encodedKey;
    }
    
    
    
    public void genKeys(){
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(DEFAULT_ENCRYPTION_ALGORITHM);
            kpg.initialize(DEFAULT_ENCRYPTION_KEY_LENGTH);
            
            KeyPair keyPair = kpg.generateKeyPair();
            mPublicKey = keyPair.getPublic();
            mPrivateKey = keyPair.getPrivate();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public byte[] encryptText(String text){
        byte[] encryptedText = null;
        try {
            
            Cipher cipher = Cipher.getInstance(DEFAULT_TRANSFORMATION);
            cipher.init(Cipher.PUBLIC_KEY, cPublicKey);
            encryptedText = cipher.doFinal(text.getBytes());
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return encryptedText;
    }
    public byte[] decryptText(byte[] encryptedText){
        byte[] decryptedText = null;
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_TRANSFORMATION);
            cipher.init(Cipher.PRIVATE_KEY, mPrivateKey);
            
            decryptedText = cipher.doFinal(encryptedText);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return decryptedText;
    }
    
}
