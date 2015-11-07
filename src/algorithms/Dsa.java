package src.algorithms;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Optional;

import javax.crypto.spec.SecretKeySpec;

public class DSA {
	public static byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}

	public static Optional<byte[]> sign(String key, String message, String algorithm){
		try {
//			int keyLength = 1024;
//			
//			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
//			keyGen.initialize(keyLength);
//			KeyPair keyPair = keyGen.generateKeyPair();
//			byte[] publickey = keyPair.getPublic().getEncoded().toString().getBytes();
//			byte[] privatekey = keyPair.getPrivate().getEncoded().toString().getBytes();
//			String publickeystring = new String(publickey, Charset.forName("UTF-8"));
//			String privatekeystring = new String(privatekey, Charset.forName("UTF-8"));
//			System.out.println("publickey: "+publickeystring);
//			System.out.println("privatekey: "+privatekeystring);
			
		
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key.getBytes());
//			SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), algorithm);
			KeyFactory keyFactory = KeyFactory.getInstance("DSA");
			PrivateKey privatekey = keyFactory.generatePrivate(keySpec);
			
//			nao sei se da certo
			System.out.println("privatekey: "+privatekey.toString());			
			
			Signature sig = Signature.getInstance(algorithm+"WithDSA");
			sig.initSign(privatekey);
			byte[] byteMessage = message.getBytes("UTF8"); 
			sig.update(byteMessage);
			byte[] signature = sig.sign();
			return Optional.of(signature);
//			return Optional.of("SEI LA".getBytes());
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Algoritmo errado!");
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			System.out.println("Encoding n�o suportado!");
			e.printStackTrace();
		} catch (SignatureException e) {
			System.out.println("Erro n� assinatura!");
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			System.out.println("Chave privada inv�lida!");
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			System.out.println("KeySpec inv�lido!");
			e.printStackTrace();
		}
		
		return Optional.empty();
	}

	public static Optional<byte[]> verify(String key, String signature, String algorithm){

		try {
			// tenho que reutilizar aquele keypair
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(1024);
			KeyPair publickey = keyGen.generateKeyPair();



			byte[] byteSignature = hexStringToByteArray(signature);

			Signature sig = Signature.getInstance(algorithm+"WithRSA");
			String output;
			
			sig.initVerify(publickey.getPublic());
			// sig.update(plainText);
			
			if (sig.verify(byteSignature)){
				output = "Assinatura verificada";
			} else {
				output = "Assinatura falsa!!!";
			}
			
			return Optional.of(output.getBytes());
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Algoritmo errado!");
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			System.out.println("A chave do HMAC � incopat�vel!");
			e.printStackTrace();
		} catch (SignatureException e) {
			System.out.println("Erro na assinatura!");
			e.printStackTrace();
		}
		return Optional.empty();
	}
}