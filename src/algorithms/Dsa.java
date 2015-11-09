package src.algorithms;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.SecureRandom;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Optional;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class DSA {
	public static KeyPair createKeyPair(){
		try {
			KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
			keyGenerator.initialize(1024, new SecureRandom());
			return keyGenerator.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Algoritmo errado!");
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static byte[] getDigest(byte[] byteMessage, String algorithm){
		try {
			MessageDigest alg_digest = MessageDigest.getInstance(algorithm);
            alg_digest.update(byteMessage);
            byte[] digest = alg_digest.digest(byteMessage);
            return digest;
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Algoritmo errado!");
			e.printStackTrace();
		}
		
		return null;
	}

	public static Optional<byte[]> sign(PrivateKey privateKey, byte[] message, String algorithm){
		try {			
			Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
   
			byte[] digest = getDigest(message, algorithm);
            byte[] signature = cipher.doFinal(digest);
			
			return Optional.of(signature);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Algoritmo errado!");
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			System.out.println("Chave privada invalida!");
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			System.out.println("Nao tem padding!");
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			System.out.println("Tamanho de bloco errado!");
			e.printStackTrace();
		} catch (BadPaddingException e) {
			System.out.println("Padding não permitido!");
			e.printStackTrace();
		}
		
		return Optional.empty();
	}

	public static Optional<String> verify(PublicKey publicKey, byte[] digest, byte[] signature, String algorithm){

		try {			
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] decrypted_digest =  cipher.doFinal(signature);
            
            String output;

            if (Arrays.equals(decrypted_digest, digest)){
                output = "Assinatura verificada e correta!";
            } else {
                output = "Assinatura falsa!";
            }
			return Optional.of(output);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Algoritmo errado!");
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			System.out.println("Chave inválida!");
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			System.out.println("Não tem padding!");
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			System.out.println("Tamanho de bloco nao permitido!");
			e.printStackTrace();
		} catch (BadPaddingException e) {
			System.out.println("Padding não permitido!");
			e.printStackTrace();
		}
		return Optional.empty();
	}
}