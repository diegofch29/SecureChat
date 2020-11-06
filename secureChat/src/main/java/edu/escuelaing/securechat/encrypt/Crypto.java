/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.securechat.encrypt;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


/**
 *
 * @author diego
 */
public class Crypto {   
    
    // key encryption algorithms supported - RSA, Diffie-Hellman, DSA
// key pair generator - RSA: keyword - RSA, key size: 1024, 2048
// key pair generator - Diffie-Hellman: keyword i DiffieHellman, key size - 1024
// key pair generator - DSA: keyword - DSA, key size: 1024
// NOTE: using asymmetric algorithms other than RSA needs to be worked out 
protected static String DEFAULT_ENCRYPTION_ALGORITHM = "RSA";
protected static int DEFAULT_ENCRYPTION_KEY_LENGTH = 1024;
protected static String DEFAULT_TRANSFORMATION = "RAS/ECB/PKCS1Padding";

protected String mEncryptionAlgorithm, mTransformation;
protected int mEncryptionKeyLength;
protected PublicKey mPublicKey;
protected PrivateKey mPrivateKey;
    
    public Crypto() {
        mEncryptionAlgorithm = Crypto.DEFAULT_ENCRYPTION_ALGORITHM;
        mEncryptionKeyLength = Crypto.DEFAULT_ENCRYPTION_KEY_LENGTH;
        mTransformation = Crypto.DEFAULT_TRANSFORMATION;
        mPublicKey = null;
        mPrivateKey = null;
    }
    
    public static BigInteger keyToNumber(byte[] byteArray){
        return new BigInteger(1, byteArray);
    }
    
    public String getEncryptionAlgorithm(){
        return mEncryptionAlgorithm;
    }
    
    public int getEncryptionKeyLength(){
        return mEncryptionKeyLength;
    }
    
    public String getTransformation(){
        return mTransformation;
    }
    
    public PublicKey getPublicKey(){
        return mPublicKey;
    }
    
    public byte[] getPublicKeyAsByteArray(){
        return mPublicKey.getEncoded();
    }
    
    public String getEncodedPublicKey(){
        String encodedKey = Base64.getEncoder().encodeToString(mPublicKey.getEncoded());
        return encodedKey;
    }
    
    public PrivateKey getPrivateKey(){
        return mPrivateKey;
    }
    
    public byte[] getPrivateKeyAsByteArray(){
        return mPrivateKey.getEncoded();
    }
    
    public String getEncodedPrivateKey(){
        String encodedKey = Base64.getEncoder().encodeToString(mPrivateKey.getEncoded());
        return encodedKey;
    }   
    
    public byte[] encryptText(String text){
        byte[] encryptedText = null;
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(mEncryptionAlgorithm);
            kpg.initialize(mEncryptionKeyLength);
            
            KeyPair keyPair = kpg.generateKeyPair();
            
            mPublicKey = keyPair.getPublic();
            mPrivateKey = keyPair.getPrivate();
            Cipher cipher = Cipher.getInstance(mTransformation);
            cipher.init(Cipher.PUBLIC_KEY, mPublicKey);
            
            encryptedText = cipher.doFinal(text.getBytes());
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
        return encryptedText;
    }
    public byte[] decryptText(byte[] encryptedText){
        byte[] decryptedText = null;
        try {
            Cipher cipher = Cipher.getInstance(mTransformation);
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
