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
		
//		String P = "dc5bf3a88b2d99e4c95cdd7a0501cc38630d425cf5c390af3429cff1f35147b795caea923f0d3577158f8a0c89dabd1962c2c453306b5d70cacfb01430aceb54e5a5fa6f9340d3bd2da612fceeb76b0ec1ebfae635a56ab141b108e00dc76eefe2edd0c514c21c457457c39065dba9d0ecb7569c247172d8438ad2827b60435b";
//		String Q = "e956602b83d195dbe945b3ac702fc61f81571f1d";
//		String G = "d7eb9ca20a3c7a079606bafc4c9261ccaba303a5dc9fe9953f197dfe548c234895baa77f441ee6a2d97b909cbbd26ff7b869d24cae51b5c6edb127a4b5d75cd8b46608bfa148249dffdb59807c5d7dde3fe3080ca3a2d28312142becb1fa8e24003e21c7287108174b95d5bc711e1c8d9b1076784f5dc37a964a5e51390da713";
//		
//		String msg = "b966012b318dafbe4ba7c94e6ebf59165a1ebc5d5a4b279a2e314a502860513eaf1d1dbc1c10a8aa519b204006244e53e83451bdbe9dea8c922387bd5b2e59b61270e31b26ec2c34996c2bddcb85ad01f1c23d4f1ed716d2a179189be0dee32f2f71ce6fae80019f8009c55b6e4fc9b215a4687a776087a6c33e5ce5d564ef11";
//		String X = "0af439b886144504d84de0616d151fe1d1f5ad06";
//		String Y = "3d9dcb5a51f4a5c3ac315a255fc2d1f8f9b2da4921a057403d31c4a4e000bfecc4ed5aa7432c1791073b46ed8781993486d0b8cf8c5674d3ec87e03fbcc228c7510996fedef4b42e69788adc771920ea75991e24c14cce9b3e703512729a75d6ac2cebd513cc145f03548d5fd8ddef23932c7b39ad4f2db50f6a304121453b4c";
//		String R = "60b989352fbe35be141b0db29c9dd22b55b65fd1";
//		String S = "20b375c40e01300209ccf2e3db0681eccb03b64a";
//		
//		BigInteger p = new BigInteger(P, 16);
//		BigInteger q = new BigInteger(Q, 16);
//		BigInteger g = new BigInteger(G, 16);
//		BigInteger x = new BigInteger(X, 16);
//		BigInteger y = new BigInteger(Y, 16);
//		BigInteger r = new BigInteger(R, 16);
//		BigInteger s = new BigInteger(S, 16);
//		
//		PublicKey publicKey = DSA.createPublicKeyByParams(p, q, g, y);
//		PrivateKey privateKey = DSA.createPrivateKeyByParams(p, q, g, x);
//		
//		byte[] bytePublicKey = publicKey.getEncoded();
//		byte[] bytePrivateKey = privateKey.getEncoded();
//		
//		JTextArea publicKeyTextArea = getTa_publickey();
//		JTextArea privateKeyTextArea = getTa_privatekey();
//		JTextArea messageTextArea = getTa_message();
//		JTextArea signatureTextArea = getTa_signature();
//		JTextArea hashTextArea = getTa_hash();
//		
//		String signature = DatatypeConverter.printHexBinary(DSA.derEncode(r, s));
//		
//		publicKeyTextArea.setText(DatatypeConverter.printHexBinary(bytePublicKey));
//		privateKeyTextArea.setText(DatatypeConverter.printHexBinary(bytePrivateKey));
//		messageTextArea.setText(msg);
//		signatureTextArea.setText(signature);
//		String algo = HMAC_ALGORITHMS().get((String)getCbox_mode().getSelectedItem());
//		byte[] digest = DSA.getDigest(Utils.hexStringToByteArray(msg), algo);
//		hashTextArea.setText(DatatypeConverter.printHexBinary(digest));
	}
	 /* This method initializes jContentPane
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
			KeyFactory keyFactory = KeyFactory.getInstance("DSA");
			PublicKey publicKey = keyFactory.generatePublic(keySpec);
			
			String signature = getTa_signature().getText();
			byte[] byteSignature = Utils.hexStringToByteArray(signature);
			String digest = getTa_hash().getText();
			byte[] byteDigest = Utils.hexStringToByteArray(digest);
			String message = getTa_message().getText();
			byte[] byteMessage = Utils.hexStringToByteArray(message);
			String hashAlgorithm = HMAC_ALGORITHMS().get((String)getCbox_mode().getSelectedItem());

			if(byteDigest.length != 0 && byteSignature.length != 0 && signature != null && publicKey != null && hashAlgorithm != null){
				Optional<String> output = DSA.verify(publicKey, byteDigest, byteSignature, hashAlgorithm, byteMessage);
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
		map.put("SHA-1", "SHA-1");
		map.put("SHA-256", "SHA-256");
		
		return map;
	}


}  //  @jve:decl-index=0:visual-constraint="10,10"