package src.algorithms;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Hmac {

	public static Optional<byte[]> encript(byte[] key, byte[] message, String algorithm){
		try {
			Mac hmac = Mac.getInstance(algorithm);
			SecretKeySpec secretKey = new SecretKeySpec(key, algorithm);
			hmac.init(secretKey);
			hmac.update(message);
			
			return Optional.of(hmac.doFinal());
		} catch (NoSuchAlgorithmException e) {
			System.out.println("HMAC - algoritmo errado!");
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			System.out.println("A chave do HMAC é incopatível!");
			e.printStackTrace();
		}
		
		return Optional.empty();
	}
	
}
