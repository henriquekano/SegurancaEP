package src.algorithms;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Optional;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class DSA {
	
	public static Optional<byte[]> sign(byte[] message, PrivateKey key){
//		https://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html#Cipher
		try {
			Signature dsa = Signature.getInstance("SHA256withRSA");
			dsa.initSign(key);
			
			return Optional.of(dsa.sign());
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Algoritmo errado no DSA!");
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			System.out.println("Chave invalida na assinatura do DSA");
			e.printStackTrace();
		} catch (SignatureException e) {
			System.out.println("Erro na assinatura no DSA!");
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	public static boolean verify(byte[] message, Signature signature, PublicKey key){
//		https://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html#Cipher
		try {
			signature.initVerify(key);
			return signature.verify(message);
		} catch (SignatureException e) {
			System.out.println("Erro na  verificacao da assinatura do DSA");
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			System.out.println("Chave publica usada na verificacao do DSA invalida!");
			e.printStackTrace();
		}
		return false;
	}
//	
//	public static KeyPair createKeyPair(){
//		try {
//			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//			KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
//			keyGenerator.initialize(2048, new SecureRandom());
//			return keyGenerator.generateKeyPair();
//		} catch (NoSuchAlgorithmException e) {
//			System.out.println("Erro na criação de chaves da RSA");
//			e.printStackTrace();
//		}
//		
//		return null;
//	}
}
