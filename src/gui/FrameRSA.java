package src.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
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
	
	private static KeyFactory kf;
	private static PublicKey publicKey;
	private static PrivateKey privateKey;
	private byte[] encryptedMessage;
	
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
		try {
			kf = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setSize(620, 500);
		this.setContentPane(getJContentPane());
		this.setTitle("RSA Encryption");
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
		JTextArea keyTextArea = getTa_publickey();
		
		
		try {
			byte[] bytesPublicKey = Utils.hexStringToByteArray(getTa_publickey().getText());
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytesPublicKey);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey publicKey = keyFactory.generatePublic(keySpec);
			
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
		JTextArea mesageTextArea = getTa_plantext();
		byte[] byteEncrypted = Utils.hexStringToByteArray(getTa_cyphertext().getText());
		byte[] bytesPrivateKey = Utils.hexStringToByteArray(getTa_privatekey().getText());
		
		try {			
			if(byteEncrypted.length > 0){
				PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytesPrivateKey);
				KeyFactory keyFactory = KeyFactory.getInstance("RSA");
				PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
				
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
