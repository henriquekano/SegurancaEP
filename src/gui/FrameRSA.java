package src.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.xml.bind.DatatypeConverter;

import src.algorithms.RSA;
import src.algorithms.utils.Utils;

public class FrameRSA extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JButton bt_generatePairKey = null;
	private JButton bt_encrypt = null;
	private JButton bt_decrypt = null;
	private JLabel jLabel_privatekey;
	private JLabel jLabel_publickey;
	private JLabel jLabel_plantext;
	private JLabel jLabel_cyphertext;
	
	private JPanel jPanel_Input = null;
	private JTextArea ta_privatekey;
	private JTextArea ta_publickey;
	private JTextArea ta_plantext;
	private JTextArea ta_cyphertext;
	
	/**
	 * This is the default constructor
	 */
	public FrameRSA() {
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
		this.setTitle("RSA Encryption");
		
//		Utilizados para vetores de testes.
//		String N = "A8B3B284AF8EB50B387034A860F146C4919F318763CD6C5598C8AE4811A1E0ABC4C7E0B082D693A5E7FCED675CF4668512772C0CBC64A742C6C630F533C8CC72F62AE833C40BF25842E984BB78BDBF97C0107D55BDB662F5C4E0FAB9845CB5148EF7392DD3AAFF93AE1E6B667BB3D4247616D4F5BA10D4CFD226DE88D39F16FB";
//		String E = "010001";
//		String D = "53339CFDB79FC8466A655C7316ACA85C55FD8F6DD898FDAF119517EF4F52E8FD8E258DF93FEE180FA0E4AB29693CD83B152A553D4AC4D1812B8B9FA5AF0E7F55FE7304DF41570926F3311F15C4D65A732C483116EE3D3D2D0AF3549AD9BF7CBFB78AD884F84D5BEB04724DC7369B31DEF37D0CF539E9CFCDD3DE653729EAD5D1";
//		String P = "D32737E7267FFE1341B2D5C0D150A81B586FB3132BED2F8D5262864A9CB9F30AF38BE448598D413A172EFB802C21ACF1C11C520C2F26A471DCAD212EAC7CA39D";
//		String Q = "CC8853D1D54DA630FAC004F471F281C7B8982D8224A490EDBEB33D3E3D5CC93C4765703D1DD791642F1F116A0DD852BE2419B2AF72BFE9A030E860B0288B5D77";
//		String DP = "0E12BF1718E9CEF5599BA1C3882FE8046A90874EEFCE8F2CCC20E4F2741FB0A33A3848AEC9C9305FBECBD2D76819967D4671ACC6431E4037968DB37878E695C1";
//		String DQ = "95297B0F95A2FA67D00707D609DFD4FC05C89DAFC2EF6D6EA55BEC771EA333734D9251E79082ECDA866EFEF13C459E1A631386B7E354C899F5F112CA85D71583";
//		String QINV = "4F456C502493BDC0ED2AB756A3A6ED4D67352A697D4216E93212B127A63D5411CE6FA98D5DBEFD73263E3728142743818166ED7DD63687DD2A8CA1D2F4FBD8E1";
//		
//		String Msg = "6628194E12073DB03BA94CDA9EF9532397D50DBA79B987004AFEFE34";
//		
//		BigInteger n = new BigInteger(N, 16);
//		BigInteger e = new BigInteger(E, 16);
//		BigInteger d = new BigInteger(D, 16);
//		BigInteger p = new BigInteger(P, 16);
//		BigInteger q = new BigInteger(Q, 16);
//		BigInteger dP = new BigInteger(DP, 16);
//		BigInteger dQ = new BigInteger(DQ, 16);
//		BigInteger qInv = new BigInteger(QINV, 16);
//		
//		PublicKey publicKey = RSA.createPublicKeyByParams(n, e);
//		PrivateKey privateKey = RSA.createPrivateKeyByParams(n, e, d, p, q, dP, dQ, qInv);
//		
//		byte[] byteMessage = Utils.hexStringToByteArray(Msg);
//		
//		byte[] bytePublicKey = publicKey.getEncoded();
//		byte[] bytePrivateKey = privateKey.getEncoded();
//		
//		JTextArea publicKeyTextArea = getTa_publickey();
//		JTextArea privateKeyTextArea = getTa_privatekey();
//		JTextArea plainTextArea = getTa_plantext();
//		JTextArea cipherTextArea = getTa_cyphertext();
//		
//		publicKeyTextArea.setText(DatatypeConverter.printHexBinary(bytePublicKey));
//		privateKeyTextArea.setText(DatatypeConverter.printHexBinary(bytePrivateKey));
//		plainTextArea.setText(Msg);	
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			
			jContentPane.add(getBt_encrypt(), null);
			jContentPane.add(getBt_decrypt(), null);
			jContentPane.add(getJPanel_Input(), null);
						
		}
		return jContentPane;
	}


	private void rsa_generatePairKey() {
		KeyPair keyPair = RSA.createKeyPair();
		PublicKey publicKey = keyPair.getPublic();
		PrivateKey privateKey = keyPair.getPrivate();
		
		JTextArea publicKeyTextArea = getTa_publickey();
		JTextArea privateKeyTextArea = getTa_privatekey();

		byte[] bytePublicKey = publicKey.getEncoded();
		byte[] bytePrivateKey = privateKey.getEncoded();

		publicKeyTextArea.setText(DatatypeConverter.printHexBinary(bytePublicKey));
		privateKeyTextArea.setText(DatatypeConverter.printHexBinary(bytePrivateKey));		
	}
	
	private void rsa_encrypt() {
		String message = getTa_plantext().getText();
		byte[] byteMessage = Utils.hexStringToByteArray(message);
		JTextArea encryptedTextArea = getTa_cyphertext();
		
		try {
			byte[] bytesPublicKey = Utils.hexStringToByteArray(getTa_publickey().getText());
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytesPublicKey);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
			
			if(byteMessage.length > 0){
				Optional<byte[]> encryptedMessage = RSA.encrypt(byteMessage, publicKey);
				
				if(encryptedMessage.isPresent()){
					encryptedTextArea.setText(Utils.toHexString(encryptedMessage.get()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}


	private void rsa_decrypt() {
		byte[] byteEncrypted = Utils.hexStringToByteArray(getTa_cyphertext().getText());
		byte[] bytesPrivateKey = Utils.hexStringToByteArray(getTa_privatekey().getText());
		
		try {			
			if(byteEncrypted.length > 0){
				PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytesPrivateKey);
				KeyFactory keyFactory = KeyFactory.getInstance("RSA");
				PrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
				
				Optional<byte[]> decryptedMessage = Optional.empty();
				
				decryptedMessage = RSA.decrypt(byteEncrypted, privateKey);
				
				if(decryptedMessage.isPresent()){
					JOptionPane.showMessageDialog(null, "Mensagem Decifrada: "+Utils.toHexString(decryptedMessage.get()));
				}
			}		
			
		} catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
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
			bt_generatePairKey.setBounds(12, 18, 169, 29);
			bt_generatePairKey.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					rsa_generatePairKey();
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
	private JButton getBt_encrypt() {
		if (bt_encrypt == null) {
			bt_encrypt = new JButton();
			bt_encrypt.setFont(new Font("Tahoma", Font.BOLD, 14));
			bt_encrypt.setBounds(new Rectangle(170, 402, 113, 29));
			bt_encrypt.setText("Encrypt");
			bt_encrypt.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					rsa_encrypt();
				}
			});
		}
		return bt_encrypt;
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
			bt_decrypt.setBounds(new Rectangle(423, 402, 113, 29));
			bt_decrypt.setText("Decrypt");
			bt_decrypt.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					rsa_decrypt();
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
			jPanel_Input.setBorder(new TitledBorder(new LineBorder(new Color(171, 173, 179)), "RSA Parametes", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
			jPanel_Input.setToolTipText("");
			jPanel_Input.setLayout(null);
			jPanel_Input.setBounds(new Rectangle(12, 25, 578, 354));

			jLabel_privatekey = new JLabel();
			jLabel_privatekey.setFont(new Font("Tahoma", Font.BOLD, 14));
			jLabel_privatekey.setBounds(12, 54, 90, 28);
			jPanel_Input.add(jLabel_privatekey);
			jLabel_privatekey.setText("Private Key:");
			
			jLabel_publickey = new JLabel();
			jLabel_publickey.setFont(new Font("Tahoma", Font.BOLD, 14));
			jLabel_publickey.setBounds(12, 101, 90, 28);
			jPanel_Input.add(jLabel_publickey);
			jLabel_publickey.setText("Public Key:");

			jLabel_plantext = new JLabel();
			jLabel_plantext.setFont(new Font("Tahoma", Font.BOLD, 14));
			jLabel_plantext.setBounds(12, 173, 81, 28);
			jPanel_Input.add(jLabel_plantext);
			jLabel_plantext.setText("Plan Text:");
			
			jLabel_cyphertext = new JLabel();
			jLabel_cyphertext.setFont(new Font("Tahoma", Font.BOLD, 14));
			jLabel_cyphertext.setBounds(12, 274, 90, 28);
			jPanel_Input.add(jLabel_cyphertext);
			jLabel_cyphertext.setText("Cypher Text:");

			jPanel_Input.add(getTa_privatekey());
			jPanel_Input.add(getTa_publickey());
			jPanel_Input.add(getTa_plantext());
			jPanel_Input.add(getTa_cyphertext());
			jPanel_Input.add(getBt_generateKeyPair());

		}
		return jPanel_Input;
	}
	
	private JTextArea getTa_privatekey() {
		if (ta_privatekey == null) {
			ta_privatekey = new JTextArea();
			ta_privatekey.setLineWrap(true);
			ta_privatekey.setFont(new Font("Tahoma", Font.PLAIN, 12));
			ta_privatekey.setBounds(116, 60, 450, 28);
		}
		return ta_privatekey;
	}
	
	private JTextArea getTa_publickey() {
		if (ta_publickey == null) {
			ta_publickey = new JTextArea();
			ta_publickey.setLineWrap(true);
			ta_publickey.setFont(new Font("Tahoma", Font.PLAIN, 12));
			ta_publickey.setBounds(116, 107, 450, 28);
		}
		return ta_publickey;
	}
	
	private JTextArea getTa_plantext() {
		if (ta_plantext == null) {
			ta_plantext = new JTextArea();
			ta_plantext.setLineWrap(true);
			ta_plantext.setFont(new Font("Tahoma", Font.PLAIN, 12));
			ta_plantext.setBounds(116, 148, 450, 90);
		}
		return ta_plantext;
	}
	
	private JTextArea getTa_cyphertext() {
		if (ta_cyphertext == null) {
			ta_cyphertext = new JTextArea();
			ta_cyphertext.setLineWrap(true);
			ta_cyphertext.setFont(new Font("Tahoma", Font.PLAIN, 12));
			ta_cyphertext.setBounds(116, 251, 450, 90);
		}
		return ta_cyphertext;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
