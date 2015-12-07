package src.algorithms;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.DSAPublicKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Optional;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSA {

	public static Optional<byte[]> encrypt(byte[] message, PublicKey key){
//		https://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html#Cipher
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			
			return Optional.of(cipher.doFinal(message));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Algoritmo errado no RSA!");
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			System.out.println("Tipo de padding errado no RSA!");
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			System.out.println("Chave invalida na encryptacao de RSA!");
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			System.out.println("Tamanho do bloco ruim no RSA!");
			e.printStackTrace();
		} catch (BadPaddingException e) {
			System.out.println("padding ruim no RSA");
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	public static Optional<byte[]> decrypt(byte[] encryptedMessage, PrivateKey key){
//		https://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html#Cipher
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, key);
			
			return Optional.of(cipher.doFinal(encryptedMessage));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Algoritmo errado no RSA!");
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			System.out.println("Tipo de padding errado no RSA!");
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			System.out.println("Chave invalida na encryptacao de RSA!");
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			System.out.println("Tamanho do bloco ruim no RSA!");
			e.printStackTrace();
		} catch (BadPaddingException e) {
			System.out.println("padding ruim no RSA");
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	public static KeyPair createKeyPair(){
		try {
			KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
			keyGenerator.initialize(1024, new SecureRandom());
			return keyGenerator.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Erro na criação de chaves da RSA");
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static PublicKey createPublicKeyByParams(BigInteger n, BigInteger exponent) {
	    try {
			KeyFactory factory = KeyFactory.getInstance("RSA");
			RSAPublicKeySpec pubSpec = new RSAPublicKeySpec(n, exponent);
			return factory.generatePublic(pubSpec);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}	
			
	public static PrivateKey createPrivateKeyByParams(BigInteger n, BigInteger ex, BigInteger d, BigInteger p, BigInteger q, BigInteger dp, BigInteger dq, BigInteger qinv) {
	    try {
			KeyFactory factory = KeyFactory.getInstance("RSA");
			RSAPrivateCrtKeySpec privSpec = new RSAPrivateCrtKeySpec(n, ex, d, p, q, dp, dq, qinv);
			return factory.generatePrivate(privSpec);
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}	
}
