package src.gui;

import src.algorithms.*;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Rectangle;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.DSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.xml.bind.DatatypeConverter;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;

import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import src.algorithms.utils.Utils;
import sun.misc.BASE64Encoder;

public class FrameDSA extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JButton bt_generatePairKey = null;
	private JButton bt_sign = null;
	private JButton bt_decrypt = null;
	private JLabel jLabel_message;
	private JLabel jLabel_privatekey;
	private JLabel jLabel_publickey;
	private JLabel jLabel_hash;
	private JLabel jLabel_signature;
	private JLabel jLabel_mode;
	
	private JPanel jPanel_Input = null;
	private JTextArea ta_message;
	private JTextArea ta_privatekey;
	private JTextArea ta_publickey;
	private JTextArea ta_hash;
	private JTextArea ta_signature;
	private JComboBox cbox_mode;

	/**
	 * This is the default constructor
	 */
	public FrameDSA() {
		super();
		initialize();
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(620, 500);
		this.setContentPane(getJContentPane());
		this.setTitle("DSA Signature");
		
		String P = "a8f9cd201e5e35d892f85f80e4db2599a5676a3b1d4f190330ed3256b26d0e80a0e49a8fffaaad2a24f472d2573241d4d6d6c7480c80b4c67bb4479c15ada7ea8424d2502fa01472e760241713dab025ae1b02e1703a1435f62ddf4ee4c1b664066eb22f2e3bf28bb70a2a76e4fd5ebe2d1229681b5b06439ac9c7e9d8bde283";
		String Q = "f85f0f83ac4df7ea0cdf8f469bfeeaea14156495";
		String G = "2b3152ff6c62f14622b8f48e59f8af46883b38e79b8c74deeae9df131f8b856e3ad6c8455dab87cc0da8ac973417ce4f7878557d6cdf40b35b4a0ca3eb310c6a95d68ce284ad4e25ea28591611ee08b8444bd64b25f3f7c572410ddfb39cc728b9c936f85f419129869929cdb909a6a3a99bbe089216368171bd0ba81de4fe33";
		String X = "c53eae6d45323164c7d07af5715703744a63fc3a";
		String Y = "313fd9ebca91574e1c2eebe1517c57e0c21b0209872140c5328761bbb2450b33f1b18b409ce9ab7c4cd8fda3391e8e34868357c199e16a6b2eba06d6749def791d79e95d3a4d09b24c392ad89dbf100995ae19c01062056bb14bce005e8731efde175f95b975089bdcdaea562b32786d96f5a31aedf75364008ad4fffebb970b";
		
		BigInteger p = new BigInteger(P, 16);
		BigInteger q = new BigInteger(Q, 16);
		BigInteger g = new BigInteger(G, 16);
		BigInteger x = new BigInteger(X, 16);
		BigInteger y = new BigInteger(Y, 16);
		
		PublicKey publicKey = DSA.createPublicKeyByParams(p, q, g, y);
		PrivateKey privateKey = DSA.createPrivateKeyByParams(p, q, g, x);
		
		byte[] bytePublicKey = publicKey.getEncoded();
		byte[] bytePrivateKey = privateKey.getEncoded();
		
		JTextArea publicKeyTextArea = getTa_publickey();
		JTextArea privateKeyTextArea = getTa_privatekey();
		
		publicKeyTextArea.setText(DatatypeConverter.printHexBinary(bytePublicKey));
		privateKeyTextArea.setText(DatatypeConverter.printHexBinary(bytePrivateKey));
	}
	
	/**
	 * This methos converts a string representing bytes to bytes
	 * 
	 * @param s
	 * @return
	 */
	

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			
			jContentPane.add(getBt_sign(), null);
			jContentPane.add(getBt_decrypt(), null);
			jContentPane.add(getJPanel_Input(), null);

			jLabel_mode = new JLabel();
			jLabel_mode.setBounds(22, 402, 105, 28);
			jContentPane.add(jLabel_mode);
			jLabel_mode.setFont(new Font("Tahoma", Font.BOLD, 14));
			jLabel_mode.setText("Hash:");
			jContentPane.add(getCbox_mode());
						
		}
		return jContentPane;
	}

	private void dsa_generatePairKey() {
		KeyPair keyPair = DSA.createKeyPair();
		
		JTextArea publicKeyTextArea = getTa_publickey();
		JTextArea privateKeyTextArea = getTa_privatekey();
		
		publicKeyTextArea.setText(DSA.getPublicKeyHexString(keyPair));
		privateKeyTextArea.setText(DSA.getPrivateKeyHexString(keyPair));
	}
	
	private void dsa_sign() {
		try {
			byte[] bytesPrivateKey = Utils.hexStringToByteArray(getTa_privatekey().getText());
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytesPrivateKey);
			
			
			
			
			KeyFactory keyFactory = KeyFactory.getInstance("DSA");
			PrivateKey privateKey = (DSAPrivateKey) keyFactory.generatePrivate(keySpec);
			
			String message = getTa_message().getText();
			byte[] byteMessage = Utils.hexStringToByteArray(message);
			String hashAlgorithm = HMAC_ALGORITHMS().get((String)getCbox_mode().getSelectedItem());
					
			if(byteMessage.length != 0 && privateKey != null && hashAlgorithm != null){
				Optional<byte[]> signature = DSA.sign(privateKey, byteMessage, hashAlgorithm);
				if(signature.isPresent()){
					JTextArea signatureTextArea = getTa_signature();
					signatureTextArea.setText(DatatypeConverter.printHexBinary(signature.get()));
					
					byte[] digest = DSA.getDigest(byteMessage, hashAlgorithm);
					JTextArea hashTextArea = getTa_hash();
					hashTextArea.setText(DatatypeConverter.printHexBinary(digest));
				}
			}
		} catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
	}


	private void dsa_verify() {
		try {
			byte[] bytesPublicKey = Utils.hexStringToByteArray(getTa_publickey().getText());
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytesPublicKey);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey publicKey = keyFactory.generatePublic(keySpec);
			
			String signature = getTa_signature().getText();
			byte[] byteSignature = Utils.hexStringToByteArray(signature);
			String digest = getTa_hash().getText();
			byte[] byteDigest = Utils.hexStringToByteArray(digest);
			String hashAlgorithm = HMAC_ALGORITHMS().get((String)getCbox_mode().getSelectedItem());

			if(byteDigest.length != 0 && byteSignature.length != 0 && signature != null && publicKey != null && hashAlgorithm != null){
				Optional<String> output = DSA.verify(publicKey, byteDigest, byteSignature, hashAlgorithm);
				if(output.isPresent()){
					JOptionPane.showMessageDialog(null, output.get());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * This method initializes bt_generatePairKey
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBt_generateKeyPair() {
		if (bt_generatePairKey == null) {
			bt_generatePairKey = new JButton();
			bt_generatePairKey.setText("Generate a Key Pair");
			bt_generatePairKey.setHorizontalAlignment(SwingConstants.LEADING);
			bt_generatePairKey.setFont(new Font("Tahoma", Font.BOLD, 14));
			bt_generatePairKey.setBounds(new Rectangle(170, 402, 113, 29));
			bt_generatePairKey.setBounds(12, 24, 169, 29);
			bt_generatePairKey.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dsa_generatePairKey();
				}
			});
		}
		return bt_generatePairKey;
	}

	
	/**
	 * This method initializes bt_encript
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBt_sign() {
		if (bt_sign == null) {
			bt_sign = new JButton();
			bt_sign.setFont(new Font("Tahoma", Font.BOLD, 14));
			bt_sign.setBounds(new Rectangle(262, 402, 113, 29));
			bt_sign.setText("Sign");
			bt_sign.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dsa_sign();
				}
			});
		}
		return bt_sign;
	}

	
	/**
	 * This method initializes bt_decript	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBt_decrypt() {
		if (bt_decrypt == null) {
			bt_decrypt = new JButton();
			bt_decrypt.setFont(new Font("Tahoma", Font.BOLD, 14));
			bt_decrypt.setBounds(new Rectangle(452, 402, 113, 29));
			bt_decrypt.setText("Verify");
			bt_decrypt.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dsa_verify();
				}
			});
		}
		return bt_decrypt;
	}
	
	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_Input() {
		if (jPanel_Input == null) {
			jPanel_Input = new JPanel();
			jPanel_Input.setBorder(new TitledBorder(new LineBorder(new Color(171, 173, 179)), "DSA Parametes", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
			jPanel_Input.setToolTipText("");
			jPanel_Input.setLayout(null);
			jPanel_Input.setBounds(new Rectangle(12, 25, 578, 354));

			jLabel_message = new JLabel();
			jLabel_message.setFont(new Font("Tahoma", Font.BOLD, 14));
			jLabel_message.setBounds(12, 168, 81, 28);
			jPanel_Input.add(jLabel_message);
			jLabel_message.setText("Message:");

			jLabel_privatekey = new JLabel();
			jLabel_privatekey.setFont(new Font("Tahoma", Font.BOLD, 14));
			jLabel_privatekey.setBounds(12, 64, 90, 28);
			jPanel_Input.add(jLabel_privatekey);
			jLabel_privatekey.setText("Private Key:");

			jLabel_publickey = new JLabel();
			jLabel_publickey.setFont(new Font("Tahoma", Font.BOLD, 14));
			jLabel_publickey.setBounds(12, 111, 90, 28);
			jPanel_Input.add(jLabel_publickey);
			jLabel_publickey.setText("Public Key:");
			
			jLabel_hash = new JLabel();
			jLabel_hash.setFont(new Font("Tahoma", Font.BOLD, 14));
			jLabel_hash.setBounds(12, 222, 66, 28);
			jPanel_Input.add(jLabel_hash);
			jLabel_hash.setText("Hash:");
			
			jLabel_signature = new JLabel();
			jLabel_signature.setFont(new Font("Tahoma", Font.BOLD, 14));
			jLabel_signature.setBounds(12, 274, 90, 28);
			jPanel_Input.add(jLabel_signature);
			jLabel_signature.setText("Signature:");
			
			jPanel_Input.add(getTa_message());
			jPanel_Input.add(getTa_privatekey());
			jPanel_Input.add(getTa_publickey());
			jPanel_Input.add(getTa_hash());
			jPanel_Input.add(getTa_signature());
			jPanel_Input.add(getTa_publickey());
			jPanel_Input.add(getBt_generateKeyPair());
		}
		return jPanel_Input;
	}
	
	private JTextArea getTa_message() {
		if (ta_message == null) {
			ta_message = new JTextArea();
			ta_message.setLineWrap(true);
			ta_message.setFont(new Font("Tahoma", Font.PLAIN, 12));
			ta_message.setBounds(116, 158, 450, 51);
		}
		return ta_message;
	}

	private JTextArea getTa_privatekey() {
		if (ta_privatekey == null) {
			ta_privatekey = new JTextArea();
			ta_privatekey.setLineWrap(true);
			ta_privatekey.setFont(new Font("Tahoma", Font.PLAIN, 12));
			ta_privatekey.setBounds(116, 60, 450, 38);;
		}
		return ta_privatekey;
	}

	private JTextArea getTa_publickey() {
		if (ta_publickey == null) {
			ta_publickey = new JTextArea();
			ta_publickey.setLineWrap(true);
			ta_publickey.setFont(new Font("Tahoma", Font.PLAIN, 12));
			ta_publickey.setBounds(116, 107, 450, 38);
		}
		return ta_publickey;
	}
	
	private JTextArea getTa_hash() {
		if (ta_hash == null) {
			ta_hash = new JTextArea();
			ta_hash.setLineWrap(true);
			ta_hash.setFont(new Font("Tahoma", Font.PLAIN, 12));
			ta_hash.setBounds(116, 218, 450, 38);
		}
		return ta_hash;
	}
	
	private JTextArea getTa_signature() {
		if (ta_signature == null) {
			ta_signature = new JTextArea();
			ta_signature.setLineWrap(true);
			ta_signature.setFont(new Font("Tahoma", Font.PLAIN, 12));
			ta_signature.setBounds(116, 272, 450, 69);
		}
		return ta_signature;
	}

	private JComboBox getCbox_mode() {
		if (cbox_mode == null) {
			cbox_mode = new JComboBox();
			String[] algorithms = HMAC_ALGORITHMS().keySet().toArray(new String[HMAC_ALGORITHMS().size()]);
			cbox_mode.setModel(new DefaultComboBoxModel<String>(algorithms));
			cbox_mode.setFont(new Font("Tahoma", Font.BOLD, 14));
			cbox_mode.setBounds(90, 402, 91, 29);
		}
		return cbox_mode;
	}

	private static final Map<String, String> HMAC_ALGORITHMS(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("SHA-1", "SHA1");
		map.put("SHA-256", "SHA256");
		
		return map;
	}


}  //  @jve:decl-index=0:visual-constraint="10,10"