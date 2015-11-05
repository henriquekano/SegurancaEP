package src.algorithms;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
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
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
			keyGenerator.initialize(2048, new SecureRandom());
			return keyGenerator.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Erro na criação de chaves da RSA");
			e.printStackTrace();
		}
		
		return null;
	}
}
