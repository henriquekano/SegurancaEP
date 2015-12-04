package src.algorithms;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Hmac {

	public static Optional<byte[]> encript(String key, String message, String algorithm){
		try {
			Mac sha256Hmac = Mac.getInstance(algorithm);
			SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), algorithm);
			sha256Hmac.init(secretKey);
			sha256Hmac.update(message.getBytes());
			
			return Optional.of(sha256Hmac.doFinal());
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
