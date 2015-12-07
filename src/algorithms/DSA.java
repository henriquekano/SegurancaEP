package src.algorithms;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.spec.DSAPrivateKeySpec;
import java.security.spec.DSAPublicKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;
import java.util.Optional;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;

import org.bouncycastle.asn1.DERInteger;
import org.bouncycastle.asn1.DERSequenceGenerator;
import org.bouncycastle.crypto.params.DSAParameters;
import org.bouncycastle.crypto.params.DSAPublicKeyParameters;
import org.bouncycastle.crypto.signers.DSASigner;

public class DSA {
	public static KeyPair createKeyPair(){
		try {
			KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("DSA");
			keyGenerator.initialize(1024);
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
			Signature signer = Signature.getInstance(algorithm.replace("-", "")+"withDSA");
		    signer.initSign(privateKey);
		    signer.update(message);
		
			return Optional.of(signer.sign());	
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Algoritmo errado!");
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			System.out.println("Chave privada invalida!");
			e.printStackTrace();
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Optional.empty();
	}

	public static Optional<String> verify(PublicKey publicKey, byte[] digest, byte[] signature, String algorithm, byte[] message){

		try {		
			Signature signer = Signature.getInstance(algorithm.replace("-", "")+"withDSA");
		    signer.initVerify(publicKey);
		    signer.update(message);
		    
		    String output;

            if (signer.verify(signature)){
                output = "Assinatura válida!";
            } else {
                output = "Assinatura inválida!";
            }
			return Optional.of(output);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Algoritmo errado!");
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			System.out.println("Chave inválida!");
			e.printStackTrace();
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	public static PublicKey createPublicKeyByParams(BigInteger p, BigInteger q, BigInteger g, BigInteger y) {
	    try {
			KeyFactory keyFactory = KeyFactory.getInstance("DSA");
			KeySpec publicKeySpec = new DSAPublicKeySpec(y, p, q, g);
			return keyFactory.generatePublic(publicKeySpec);
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
	
	public static PrivateKey createPrivateKeyByParams(BigInteger p, BigInteger q, BigInteger g, BigInteger x) {
	    try {
			KeyFactory keyFactory = KeyFactory.getInstance("DSA");
			KeySpec privateKeySpec = new DSAPrivateKeySpec(x, p, q, g);
			return keyFactory.generatePrivate(privateKeySpec);
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
	
	public static byte[] derEncode(BigInteger r, BigInteger s) {
        try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DERSequenceGenerator seqGen = new DERSequenceGenerator(bos);
 
			seqGen.addObject(new DERInteger(r));
			seqGen.addObject(new DERInteger(s));
			seqGen.close();
 
			byte[] asnsignature = bos.toByteArray();
			bos.close();
			seqGen.close();
			return asnsignature;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
        
    }
	
	public static String getPublicKeyHexString(KeyPair keyPair){
		PublicKey publicKey = keyPair.getPublic();
		byte[] bytePublicKey = publicKey.getEncoded();
		return DatatypeConverter.printHexBinary(bytePublicKey);
	}
	
	public static String getPrivateKeyHexString(KeyPair keyPair){
		PrivateKey privateKey = keyPair.getPrivate();
		byte[] bytePrivateKey = privateKey.getEncoded();
		return DatatypeConverter.printHexBinary(bytePrivateKey);
	}
}